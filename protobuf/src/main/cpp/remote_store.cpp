//
// Created by summer on 2024/11/4.
//

#include "remote_store.h"

void RemoteStore::GetPolicy(const UserInfo &info, Policy *policy) {
    policy->set_background(20);
    policy->set_enable(true);
    policy->set_describe("ss");
}
