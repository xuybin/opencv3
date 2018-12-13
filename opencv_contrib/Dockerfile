FROM ubuntu:18.04

# Set environmental variables
ENV ANDROID_SDK /opt/android/sdk
ENV ANDROID_NDK ${ANDROID_SDK}/ndk-bundle
ENV OPENCV_DIR /opencv
ENV OPENCV_CONTRIB_DIR /opencv_contrib
ENV OPENCV_ANDROID /opencv_android
ENV PATH=${ANDROID_SDK}/emulator:${ANDROID_SDK}/tools:${ANDROID_SDK}/tools/bin:${ANDROID_SDK}/platform-tools:${ANDROID_NDK}:${PATH}

# Download and install Android SDK
RUN sudo apt-get install -y python ant openjdk-8-jdk cmake unzip && apt-get autoremove -y openjdk-11-jre-headless && java -version && mkdir -p ${ANDROID_SDK} ${ANDROID_NDK} ${OPENCV_DIR} ${OPENCV_CONTRIB_DIR} ${OPENCV_ANDROID} && \
  wget -q https://dl.google.com/android/repository/tools_r25.2.5-linux.zip  && unzip -q tools_r25.2.5-linux.zip -d ${ANDROID_SDK} && \
  wget -q http://dl.google.com/android/ndk/android-ndk-r10e-linux-x86_64.bin && chmod +x android-ndk-r10e-linux-x86_64.bin && ./android-ndk-r10e-linux-x86_64.bin && rm -rf ./android-ndk-r10e-linux-x86_64.bin  && mv ./android-ndk-r10e/* ${ANDROID_NDK}/ && rm -rf ./android-ndk-r10e && \
  wget -q https://github.com/opencv/opencv/archive/3.4.4.zip && unzip -q 3.4.4.zip && rm -rf 3.4.4.zip && mv ./opencv-3.4.4 ${OPENCV_DIR}/ && rm -rf  ./opencv-3.4.4   && \
  wget -q https://github.com/opencv/opencv_contrib/archive/3.4.4.zip && unzip -q 3.4.4.zip && rm -rf 3.4.4.zip && mv ./opencv-3.4.4 ${OPENCV_CONTRIB_DIR}/ && rm -rf  ./opencv-3.4.4   && \
  mkdir -p ${ANDROID_SDK}/licenses && echo -e "\nd56f5187479451eabf01fb78af6dfcb131a6481e" >${ANDROID_SDK}/licenses/android-sdk-license && sdkmanager "platform-tools" "platforms;android-25" "build-tools;25.0.3" "emulator" "extras;android;m2repository" "extras;google;m2repository" "extras;google;google_play_services" && \
  sdkmanager --list  && \
  python2 ${OPENCV_DIR}/platforms/android/build_sdk.py --config ndk-10.config.py --extra_modules_path ${OPENCV_CONTRIB_DIR}/modules --sdk_path ${ANDROID_SDK} --ndk_path ${ANDROID_NDK} ${OPENCV_ANDROID} ${OPENCV_DIR}
