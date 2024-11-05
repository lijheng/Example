//
// Created by summer on 2024/11/4.
//

#ifndef MINDCONTROL_REMOTE_STORE_H
#define MINDCONTROL_REMOTE_STORE_H

#include "example.pb.h"
using namespace com::summer::example::protobuf;
class RemoteStore {
public:
    static void GetPolicy(const UserInfo& info, Policy* policy);
};


#endif //MINDCONTROL_REMOTE_STORE_H
