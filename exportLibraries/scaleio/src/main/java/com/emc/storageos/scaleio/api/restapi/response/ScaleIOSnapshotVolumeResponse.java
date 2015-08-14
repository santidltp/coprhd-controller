/**
 * Copyright (c) 2015 EMC Corporation
 * All Rights Reserved
 *
 * This software contains the intellectual property of EMC Corporation
 * or is licensed to EMC Corporation from third parties.  Use of this
 * software and the intellectual property contained therein is expressly
 * limited to the terms and conditions of the License Agreement under which
 * it is provided by or on behalf of EMC.
 */
package com.emc.storageos.scaleio.api.restapi.response;

import java.util.List;

/**
 * Create volume snapshot response
 * 
 */
public class ScaleIOSnapshotVolumeResponse {
    private List<String> volumeIdList;
    private String snapshotGroupId;

    public List<String> getVolumeIdList() {
        return volumeIdList;
    }

    public void setVolumeIdList(List<String> volumeIdList) {
        this.volumeIdList = volumeIdList;
    }

    public String getSnapshotGroupId() {
        return snapshotGroupId;
    }

    public void setSnapshotGroupId(String snapshotGroupId) {
        this.snapshotGroupId = snapshotGroupId;
    }

}