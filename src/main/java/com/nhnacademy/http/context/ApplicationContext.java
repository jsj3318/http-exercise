/*
 * +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 * + Copyright 2024. NHN Academy Corp. All rights reserved.
 * + * While every precaution has been taken in the preparation of this resource,  assumes no
 * + responsibility for errors or omissions, or for damages resulting from the use of the information
 * + contained herein
 * + No part of this resource may be reproduced, stored in a retrieval system, or transmitted, in any
 * + form or by any means, electronic, mechanical, photocopying, recording, or otherwise, without the
 * + prior written permission.
 * +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 */

package com.nhnacademy.http.context;

import com.nhnacademy.http.context.exception.ObjectNotFoundException;

import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

//TODO#2 - Context를 구현합니다.
public class ApplicationContext  implements Context{
    ConcurrentMap<String, Object> objectMap;

    public ApplicationContext() {
        this.objectMap = new ConcurrentHashMap<>();
    }

    @Override
    public void setAttribute(String name, Object object) {
        //구현
        if(name == null || name.isBlank()){
            throw new IllegalArgumentException();
        }
        objectMap.put(name, object);
    }

    @Override
    public void removeAttribute(String name) {
        //구현
        objectMap.remove(name);
    }

    @Override
    public Object getAttribute(String name) {
        //object가 존재하지 않는다면 ObjectNotFoundException 예외가 발생합니다.
        if(!objectMap.containsKey(name)){
            throw new ObjectNotFoundException("존재하지 않는 id:" + name);
        }
        return objectMap.get(name);
    }
}
