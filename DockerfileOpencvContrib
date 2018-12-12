FROM ubuntu:18.04

# Set environmental variables
ENV ANDROID_HOME /opt/android/sdk
ENV ANDROID_NDK ${ANDROID_HOME}/ndk-bundle
ENV OPENCV_DIR /opencv
ENV OPENCV_CONTRIB_DIR /opencv_contrib
ENV OPENCV_ANDROID /opencv_android
ENV PATH=${ANDROID_HOME}/emulator:${ANDROID_HOME}/tools:${ANDROID_HOME}/tools/bin:${ANDROID_HOME}/platform-tools:${ANDROID_NDK}:${PATH}

# Download and install Android SDK
RUN sudo apt-get install python ant openjdk-8-jdk cmake unzip && mkdir -p ${ANDROID_HOME} ${ANDROID_NDK} ${OPENCV_DIR} ${OPENCV_CONTRIB_DIR} ${OPENCV_ANDROID} && \
  wget -q https://dl.google.com/android/repository/tools_r25.2.5-linux.zip  && unzip -q tools_r25.2.5-linux.zip -d ${ANDROID_HOME} && \
  wget -q http://dl.google.com/android/ndk/android-ndk-r10e-linux-x86_64.bin && chmod +x android-ndk-r10e-linux-x86_64.bin && ./android-ndk-r10e-linux-x86_64.bin && rm -rf ./android-ndk-r10e-linux-x86_64.bin  && mv ./android-ndk-r10e/* ${ANDROID_NDK}/ && rm -rf ./android-ndk-r10e && \
  wget -q https://github.com/opencv/opencv/archive/3.4.4.zip && unzip -q 3.4.4.zip && rm -rf 3.4.4.zip && mv ./opencv-3.4.4 ${OPENCV_DIR}/ && rm -rf  ./opencv-3.4.4   && \
  wget -q https://github.com/opencv/opencv_contrib/archive/3.4.4.zip && unzip -q 3.4.4.zip && rm -rf 3.4.4.zip && mv ./opencv-3.4.4 ${OPENCV_CONTRIB_DIR}/ && rm -rf  ./opencv-3.4.4   && \
  mkdir -p ${ANDROID_HOME}/licenses && echo -e "\nd56f5187479451eabf01fb78af6dfcb131a6481e" >${ANDROID_HOME}/licenses/android-sdk-license && sdkmanager "platform-tools" "platforms;android-25" "build-tools;25.0.3" "emulator" "extras;android;m2repository" "extras;google;m2repository" "extras;google;google_play_services" && \
  android-sdk/tools/bin/sdkmanager --list  && \
  python2 ${OPENCV_DIR}/platforms/android/build_sdk.py --config ndk-10.config.py --extra_modules_path ${OPENCV_CONTRIB_DIR}/modules --sdk_path ${ANDROID_HOME} --ndk_path ${ANDROID_NDK} ${OPENCV_ANDROID} ${OPENCV_DIR}
