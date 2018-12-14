package com.github.xuybin.opencv3

import org.opencv.core.CvType
import org.opencv.core.Mat
import org.opencv.core.MatOfInt
import org.opencv.face.EigenFaceRecognizer
import org.opencv.face.FaceRecognizer
import org.opencv.face.FisherFaceRecognizer
import org.opencv.face.LBPHFaceRecognizer
import org.opencv.imgcodecs.Imgcodecs
import org.opencv.imgcodecs.Imgcodecs.CV_LOAD_IMAGE_GRAYSCALE
import org.opencv.imgproc.Imgproc
import java.io.File
import java.lang.Exception

open class FaceSet(protected val faceRecognizer: FaceRecognizer) {
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

    // 保存特征脸库
    fun save(filename: String) {
        faceRecognizer.save(filename)
    }

    // 从目录文件训练特征脸库
    protected fun train(trainingDir: String, formatSuffix: String, getLabel: (absolutePath: String) -> Int) {
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
        val labelMats = MatOfInt().apply {
            fromList(labels)
        }
        faceRecognizer.train(images, labelMats)
    }

    // 加载特征脸库
    protected fun load(filename: String) {
        faceRecognizer.read(filename)
    }
}

/* 局部二值模式（LBP）
* 要求样本是灰度图,支持model更新。（当有新的人脸加入人脸库时，无需重新对 整个人脸库进行训练，使用update进行更新）
* PCA和LDA采用整体方法进行人脸辨别，LBP采用局部特征提取，除此之外，还有的局部特征提取方法为：
* 盖伯小波（Gabor Waelets）和离散傅里叶变换（DCT）
*
*/
class FaceSetLBPH(faceRecognizer: LBPHFaceRecognizer) : FaceSet(faceRecognizer) {
    companion object {
        fun init(
            trainingDir: String,
            formatSuffix: String,
            getLabel: (absolutePath: String) -> Int,
            radius: Int? = null,
            neighbors: Int? = null,
            gridx: Int? = null,
            gridy: Int? = null,
            threshold: Double? = null
        ): FaceSetLBPH {
            return when {
                radius == null && neighbors == null && gridx == null && gridy == null && threshold == null -> FaceSetLBPH(
                    LBPHFaceRecognizer.create()
                )
                neighbors == null && gridx == null && gridy == null && threshold == null -> FaceSetLBPH(
                    LBPHFaceRecognizer.create(radius!!)
                )
                gridx == null && gridy == null && threshold == null -> FaceSetLBPH(
                    LBPHFaceRecognizer.create(radius!!, neighbors!!)
                )
                gridy == null && threshold == null -> FaceSetLBPH(
                    LBPHFaceRecognizer.create(radius!!, neighbors!!, gridx!!)
                )
                threshold == null -> FaceSetLBPH(LBPHFaceRecognizer.create(radius!!, neighbors!!, gridx!!, gridy!!))
                else -> FaceSetLBPH(LBPHFaceRecognizer.create(radius!!, neighbors!!, gridx!!, gridy!!, threshold!!))
            }.also {
                // 训练特征脸库
                it.train(trainingDir, formatSuffix, getLabel)
            }
        }

        fun init(
            filename: String,
            radius: Int? = null,
            neighbors: Int? = null,
            gridx: Int? = null,
            gridy: Int? = null,
            threshold: Double? = null
        ): FaceSetLBPH {
            return when {
                radius == null && neighbors == null && gridx == null && gridy == null && threshold == null -> FaceSetLBPH(
                    LBPHFaceRecognizer.create()
                )
                neighbors == null && gridx == null && gridy == null && threshold == null -> FaceSetLBPH(
                    LBPHFaceRecognizer.create(radius!!)
                )
                gridx == null && gridy == null && threshold == null -> FaceSetLBPH(
                    LBPHFaceRecognizer.create(radius!!, neighbors!!)
                )
                gridy == null && threshold == null -> FaceSetLBPH(
                    LBPHFaceRecognizer.create(radius!!, neighbors!!, gridx!!)
                )
                threshold == null -> FaceSetLBPH(LBPHFaceRecognizer.create(radius!!, neighbors!!, gridx!!, gridy!!))
                else -> FaceSetLBPH(LBPHFaceRecognizer.create(radius!!, neighbors!!, gridx!!, gridy!!, threshold!!))
            }.also {
                // 加载特征脸库
                it.load(filename)
            }
        }
    }

}

/* 线性判别分析（LDA）
* 要求样本是灰度图,训练样本和识别图片大小一致,不支持model更新,训练库中至少包含两个人的
* LDA:线性鉴别的特定类投影方法，目标：实现类内方差最小，类间方差最大
*
*/
class FaceSetFisher(faceRecognizer: FisherFaceRecognizer) : FaceSet(faceRecognizer) {
    companion object {
        fun init(
            trainingDir: String,
            formatSuffix: String,
            getLabel: (absolutePath: String) -> Int,
            num_components: Int? = null,
            threshold: Double? = null
        ): FaceSetFisher {
            return when {
                num_components == null && threshold == null -> FaceSetFisher(FisherFaceRecognizer.create())
                threshold == null -> FaceSetFisher(FisherFaceRecognizer.create(num_components!!))
                else -> FaceSetFisher(FisherFaceRecognizer.create(num_components!!, threshold!!))
            }.also {
                // 训练特征脸库
                it.train(trainingDir, formatSuffix, getLabel)
            }
        }

        fun init(
            filename: String,
            num_components: Int? = null,
            threshold: Double? = null
        ): FaceSetFisher {
            return when {
                num_components == null && threshold == null -> FaceSetFisher(FisherFaceRecognizer.create())
                threshold == null -> FaceSetFisher(FisherFaceRecognizer.create(num_components!!))
                else -> FaceSetFisher(FisherFaceRecognizer.create(num_components!!, threshold!!))
            }.also {
                // 加载特征脸库
                it.load(filename)
            }
        }
    }
}

/* 线性判别分析（LDA）
* 要求样本是灰度图,训练样本和识别图片大小一致,不支持model更新
* PCA：低维子空间是使用主元分析找到的，找具有最大方差的哪个轴。
* 缺点：若变化基于外部（光照），最大方差轴不一定包括鉴别信息，不能实行分类
*/
class FaceSetEigen(faceRecognizer: EigenFaceRecognizer) : FaceSet(faceRecognizer) {
    companion object {
        fun init(
            trainingDir: String,
            formatSuffix: String,
            getLabel: (absolutePath: String) -> Int,
            num_components: Int? = null,
            threshold: Double? = null
        ): FaceSetEigen {
            return when {
                num_components == null && threshold == null -> FaceSetEigen(EigenFaceRecognizer.create())
                threshold == null -> FaceSetEigen(EigenFaceRecognizer.create(num_components!!))
                else -> FaceSetEigen(EigenFaceRecognizer.create(num_components!!, threshold!!))
            }.also {
                // 训练特征脸库
                it.train(trainingDir, formatSuffix, getLabel)
            }
        }

        fun init(
            filename: String,
            num_components: Int? = null,
            threshold: Double? = null
        ): FaceSetEigen {
            return when {
                num_components == null && threshold == null -> FaceSetEigen(EigenFaceRecognizer.create())
                threshold == null -> FaceSetEigen(EigenFaceRecognizer.create(num_components!!))
                else -> FaceSetEigen(EigenFaceRecognizer.create(num_components!!, threshold!!))
            }.also {
                // 加载特征脸库
                it.load(filename)
            }
        }
    }
}