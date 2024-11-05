
find_package(protobuf CONFIG)
if(protobuf_FOUND OR PROTOBUF_FOUND)
    message("find protobuf")
    find_package(protobuf CONFIG REQUIRED)
else()
    message("not find protobuf, using submodule")
    set(protobuf_BUILD_TESTS OFF)
    set(protobuf_BUILD_PROTOC_BINARIES OFF)
    set(protobuf_BUILD_LIBPROTOC OFF)
    add_subdirectory(../../../../../protobuf ${CMAKE_CURRENT_BINARY_DIR}/protobuf)
endif()
add_compile_definitions(GTEST_OS_WINDOWS)
message("PROTO_GENS_DIR: ${PROTO_GENS_DIR}")

# Generated sources
function(generate_protobuf_sources SRC_FILES HDR_FILES INCLUDE_ROOT)
    if(NOT ARGN)
        message(SEND_ERROR "Error: generate_protobuf_sources() called without any proto files")
        return()
    endif()
    set(${SRC_FILES})
    set(${HDR_FILES})
    set(PROTOBUF_INCLUDE_PATH -I ${INCLUDE_ROOT})
    foreach (FIL ${ARGN})
        get_filename_component(ABS_FIL ${FIL} ABSOLUTE)
        get_filename_component(FIL_WE ${FIL} NAME_WE)
        file(RELATIVE_PATH REL_FIL ${INCLUDE_ROOT} ${ABS_FIL})
        get_filename_component(REL_DIR ${REL_FIL} DIRECTORY)
        set(RELFIL_WE "${REL_DIR}/${FIL_WE}")
        list(APPEND ${SRC_FILES} "${PROTO_GENS_DIR}${RELFIL_WE}.pb.cc")
        list(APPEND ${HDR_FILES} "${PROTO_GENS_DIR}${RELFIL_WE}.pb.h")
        add_custom_command(
                OUTPUT
                "${PROTO_GENS_DIR}/${RELFIL_WE}.pb.cc"
                "${PROTO_GENS_DIR}/${RELFIL_WE}.pb.h"
                COMMAND ${PROTOBUF_PROTOC_EXECUTABLE}
                ARGS
                --cpp_out=${PROTO_GENS_DIR}
                ${PROTOBUF_INCLUDE_PATH}
                ${REL_FIL}
                WORKING_DIRECTORY ${CMAKE_CURRENT_SOURCE_DIR}
                DEPENDS ${PROTOBUF_PROTOC_EXECUTABLE} ${ABS_FIL} )
    endforeach()
    set_source_files_properties(${${SRC_FILES}} ${${HDR_FILES}} PROPERTIES GENERATED TRUE)
    set(${SRC_FILES} ${${SRC_FILES}} PARENT_SCOPE)
    set(${HDR_FILES} ${${HDR_FILES}} PARENT_SCOPE)
endfunction()