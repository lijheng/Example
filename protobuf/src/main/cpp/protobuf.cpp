#include <jni.h>
#include <string>
#include "remote_store.h"
#include "example.pb.h"

using namespace  com::summer::example::protobuf;;
extern "C" JNIEXPORT jbyteArray JNICALL
Java_com_sins_control_protocol_ProtocolLib_getPolicy(
        JNIEnv *env,
        jobject /* this */,
        jbyteArray userinfoData,
        jint len) {
    jboolean isCopy = false;
    auto _userinfo = env->GetByteArrayElements(userinfoData, &isCopy);
    UserInfo info;
    info.ParseFromArray(_userinfo, len);

    // 1 start
    Policy policy;
    RemoteStore::GetPolicy(info, &policy);
    // 1 end
    size_t policyLen = policy.ByteSizeLong();
    char* policyBytes = (char*)malloc(policyLen * sizeof(char));
    policy.SerializeToArray(policyBytes, (int)policyLen);
    jbyteArray array = env->NewByteArray((int)policyLen);
    env->SetByteArrayRegion(array, 0,(int) policyLen, reinterpret_cast<const jbyte *>(policyBytes));
    free(policyBytes);
    return array;
}