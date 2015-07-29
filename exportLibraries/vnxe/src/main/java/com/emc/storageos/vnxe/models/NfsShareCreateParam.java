/*
 * Copyright 2015 EMC Corporation
 * All Rights Reserved
 */
/**
 * Copyright (c) 2014 EMC Corporation
 * All Rights Reserved
 *
 * This software contains the intellectual property of EMC Corporation
 * or is licensed to EMC Corporation from third parties.  Use of this
 * software and the intellectual property contained therein is expressly
 * limited to the terms and conditions of the License Agreement under which
 * it is provided by or on behalf of EMC.
 */

package com.emc.storageos.vnxe.models;

import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.map.annotate.JsonSerialize;

@XmlRootElement
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class NfsShareCreateParam extends ParamBase {
    private String path;
    private NfsShareParam nfsShareParameters;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public NfsShareParam getNfsShareParameters() {
        return nfsShareParameters;
    }

    public void setNfsShareParameters(NfsShareParam nfsShareParameters) {
        this.nfsShareParameters = nfsShareParameters;
    }

}
