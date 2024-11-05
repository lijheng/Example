# For more information about using CMake with Android Studio, read the
# documentation: https://d.android.com/studio/projects/add-native-code.html.
# For more examples on how to use CMake, see https://github.com/android/ndk-samples.

# Sets the minimum CMake version required for this project.
cmake_minimum_required(VERSION 3.22.1)

project("example_protobuf")

# 设置proto可执行文件位置
set(PROTOBUF_PROTOC_EXECUTABLE "D:\\Tools\\protoc-26.1-win64\\bin\\protoc.exe" CACHE STRING "Protoc binary on host")
set(PROTO_GENS_DIR ${CMAKE_CURRENT_BINARY_DIR}/gens)

include(protobuf.cmake)

message("PROTO_BASE_DIR: ${PROTO_BASE_DIR}")
generate_protobuf_sources(EXAMPLE_PROTO_SRCS EXAMPLE_PROTO_HDRS ${PROTO_BASE_DIR} ${PROTO_BASE_DIR}/example.proto)
add_library(example_proto
        SHARED ${EXAMPLE_PROTO_HDRS} ${EXAMPLE_PROTO_SRCS})
message("EXAMPLE_PROTO_HDRS:  ${EXAMPLE_PROTO_HDRS}")
target_link_libraries(example_proto PUBLIC libprotobuf)
MESSAGE("_PROTOBUF_LIBPROTOBUF: ${_PROTOBUF_LIBPROTOBUF}  _REFLECTION： ${_REFLECTION}")
add_library(${CMAKE_PROJECT_NAME} SHARED
        # List C/C++ source files with relative paths to this CMakeLists.txt.
        ${EXAMPLE_PROTO_HDRS}
        protobuf.cpp
        remote_store.cpp
        remote_store.h)
message("PROTO_GENS_DIR: ${PROTO_GENS_DIR} CMAKE_PROJECT_NAME: ${CMAKE_PROJECT_NAME}")
target_include_directories(${CMAKE_PROJECT_NAME}
        PUBLIC ${PROTO_GENS_DIR} ${PROTOBUF_INCLUDE_DIRS})
target_link_libraries(${CMAKE_PROJECT_NAME}
        PUBLIC example_proto)