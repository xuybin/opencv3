package com.github.xuybin.opencv3

import org.opencv.core.CvType
import org.opencv.core.Mat
import org.opencv.core.MatOfInt
import org.opencv.face.FaceRecognizer
import org.opencv.face.LBPHFaceRecognizer
import org.opencv.imgcodecs.Imgcodecs
import org.opencv.imgcodecs.Imgcodecs.CV_LOAD_IMAGE_GRAYSCALE
import org.opencv.imgproc.Imgproc
import java.io.File
import java.lang.Exception

class label
interface FaceSet {
    val faceRecognizer: FaceRecognizer
    fun search(imagePath: String): Pair<Int, Double> {
        // Load the test image: ,CV_LOAD_IMAGE_GRAYSCALE
        val originalImage = Imgcodecs.imread(imagePath.trim())
        var grayImage = when (imagePath.trim().substringAfterLast(".").toLowerCase()) {
            "png" -> {
                var grayImg = Mat(originalImage.width(), originalImage.height(), CvType.CV_8UC1)
                Imgproc.cvtColor(originalImage, grayImg, Imgproc.COLOR_RGBA2GRAY)
                grayImg
            }
            "jpeg", "jpg" -> {
                var grayImg = Mat(originalImage.width(), originalImage.height(), CvType.CV_8UC1)
                Imgproc.cvtColor(originalImage, grayImg, Imgproc.COLOR_BGR2GRAY)
                grayImg
            }
            "pgm" -> {
                originalImage
            }
            else -> throw Exception("formatSuffix not one of 'png', 'jpeg','jpg' ,'pgm'")
        }

        // And get a prediction:
        val label = IntArray(1)
        val confidence = DoubleArray(1)
        faceRecognizer.predict(grayImage, label, confidence)
        return Pair(label[0], confidence[0])
    }
}

class FaceSetLBPH(override val faceRecognizer: LBPHFaceRecognizer) : FaceSet {
    companion object {
        fun init(
            trainingDir: String,
            formatSuffix: String,
            getLabel:(absolutePath:String)->Int,
            radius: Int? = null,
            neighbors: Int? = null,
            gridx: Int? = null,
            gridy: Int? = null,
            threshold: Double? = null

        ): FaceSetLBPH {
            val faceSet = when {
                radius == null && neighbors == null && gridx == null && gridy == null && threshold == null -> FaceSetLBPH(
                    LBPHFaceRecognizer.create()
                )
                neighbors == null && gridx == null && gridy == null && threshold == null -> FaceSetLBPH(
                    LBPHFaceRecognizer.create(radius!!)
                )
                gridx == null && gridy == null && threshold == null -> FaceSetLBPH(
                    LBPHFaceRecognizer.create(
                        radius!!,
                        neighbors!!
                    )
                )
                gridy == null && threshold == null -> FaceSetLBPH(
                    LBPHFaceRecognizer.create(
                        radius!!,
                        neighbors!!,
                        gridx!!
                    )
                )
                threshold == null -> FaceSetLBPH(LBPHFaceRecognizer.create(radius!!, neighbors!!, gridx!!, gridy!!))
                else -> FaceSetLBPH(LBPHFaceRecognizer.create(radius!!, neighbors!!, gridx!!, gridy!!, threshold!!))
            }
            val images = mutableListOf<Mat>()
            val labels = mutableListOf<Int>()

            val imageFiles = File(trainingDir).listFiles { dir, name ->
                name.toLowerCase().endsWith(".${formatSuffix.trim().toLowerCase().replace(".", "")}")
            }
            for (image in imageFiles) {
                // 获取标签(图片是谁)
                var label = getLabel(image.absolutePath)
                // 获取灰度图
                var img = Imgcodecs.imread(image.getAbsolutePath())

                images.add(
                    when (formatSuffix.trim().toLowerCase().replace(".", "")) {
                        "png" -> {
                            var grayImg = Mat(img.width(), img.height(), CvType.CV_8UC1)
                            Imgproc.cvtColor(img, grayImg, Imgproc.COLOR_RGBA2GRAY)
                            grayImg
                        }
                        "jpeg", "jpg" -> {
                            var grayImg = Mat(img.width(), img.height(), CvType.CV_8UC1)
                            Imgproc.cvtColor(img, grayImg, Imgproc.COLOR_BGR2GRAY)
                            grayImg
                        }
                        "pgm" -> {
                            img
                        }
                        else -> throw Exception("formatSuffix not one of 'png', 'jpeg','jpg' ,'pgm'")
                    }
                )
                labels.add(label)
            }
            val labelMats =MatOfInt().apply {
                fromList(labels)
            }
            faceSet.faceRecognizer.train(images,labelMats )
            return faceSet
        }
    }

}