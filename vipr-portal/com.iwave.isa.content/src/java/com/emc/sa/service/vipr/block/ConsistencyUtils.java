/*
 * Copyright 2015 EMC Corporation
 * All Rights Reserved
 */
package com.emc.sa.service.vipr.block;

import static com.emc.sa.service.vipr.ViPRExecutionUtils.addAffectedResources;
import static com.emc.sa.service.vipr.ViPRExecutionUtils.execute;
import static com.emc.sa.service.vipr.block.BlockStorageUtils.removeBlockResources;

import java.net.URI;
import java.util.Collections;

import com.emc.sa.service.vipr.block.consistency.tasks.ActivateConsistencyGroupFullCopy;
import com.emc.sa.service.vipr.block.consistency.tasks.CreateConsistencyGroupFullCopy;
import com.emc.sa.service.vipr.block.consistency.tasks.CreateConsistencyGroupSnapshot;
import com.emc.sa.service.vipr.block.consistency.tasks.DeactivateConsistencyGroupFullCopy;
import com.emc.sa.service.vipr.block.consistency.tasks.DeactivateConsistencyGroupSnapshot;
import com.emc.sa.service.vipr.block.consistency.tasks.DetachConsistencyGroupFullCopy;
import com.emc.sa.service.vipr.block.consistency.tasks.RestoreConsistencyGroupFullCopy;
import com.emc.sa.service.vipr.block.consistency.tasks.RestoreConsistencyGroupSnapshot;
import com.emc.sa.service.vipr.block.consistency.tasks.ResynchronizeConsistencyGroupFullCopy;
import com.emc.storageos.model.block.BlockConsistencyGroupRestRep;
import com.emc.storageos.model.block.VolumeDeleteTypeEnum;
import com.emc.vipr.client.Task;
import com.emc.vipr.client.Tasks;

/**
 * Package level Utility class with static calls to Consistency Group Tasks
 * 
 * @author Jay Logelin
 *
 */
final class ConsistencyUtils {

    private static final String VOLUME_STORAGE_TYPE = "volume";

    static boolean isVolumeStorageType(String storageType) {
        if (storageType == null) {
            return true;
        }
        return VOLUME_STORAGE_TYPE.equals(storageType);
    }

    static Tasks<BlockConsistencyGroupRestRep> createFullCopy(URI consistencyGroupId, String name, Integer count) {
        int countValue = (count != null) ? count : 1;
        Tasks<BlockConsistencyGroupRestRep> copies = execute(new CreateConsistencyGroupFullCopy(consistencyGroupId, name, countValue));
        addAffectedResources(copies);
        return copies;
    }

    static Tasks<BlockConsistencyGroupRestRep> removeFullCopy(URI consistencyGroupId, URI fullCopyId) {
        Tasks<BlockConsistencyGroupRestRep> tasks = execute(new DetachConsistencyGroupFullCopy(consistencyGroupId, fullCopyId));
        removeBlockResources(Collections.singletonList(fullCopyId), VolumeDeleteTypeEnum.FULL);
        return tasks;
    }

    static Tasks<BlockConsistencyGroupRestRep> restoreFullCopy(URI consistencyGroupId, URI fullCopyId) {
        return execute(new RestoreConsistencyGroupFullCopy(consistencyGroupId, fullCopyId));
    }

    static Tasks<BlockConsistencyGroupRestRep> detachFullCopy(URI consistencyGroupId, URI fullCopyId) {
        return execute(new DetachConsistencyGroupFullCopy(consistencyGroupId, fullCopyId));
    }

    static Tasks<BlockConsistencyGroupRestRep> resynchronizeFullCopy(URI consistencyGroupId, URI fullCopyId) {
        return execute(new ResynchronizeConsistencyGroupFullCopy(consistencyGroupId, fullCopyId));
    }

    static Tasks<BlockConsistencyGroupRestRep> activateFullCopy(URI consistencyGroupId, URI fullCopyId) {
        return execute(new ActivateConsistencyGroupFullCopy(consistencyGroupId, fullCopyId));
    }

    static Tasks<BlockConsistencyGroupRestRep> deactivateFullCopy(URI consistencyGroupId, URI fullCopyId) {
        return execute(new DeactivateConsistencyGroupFullCopy(consistencyGroupId, fullCopyId));
    }

    static Tasks<BlockConsistencyGroupRestRep> createSnapshot(URI consistencyGroupId, String snapshotName) {
        return execute(new CreateConsistencyGroupSnapshot(consistencyGroupId, snapshotName));
    }

    static Task<BlockConsistencyGroupRestRep> restoreSnapshot(URI consistencyGroupId, URI snapshotId) {
        return execute(new RestoreConsistencyGroupSnapshot(consistencyGroupId, snapshotId));
    }

    static Tasks<BlockConsistencyGroupRestRep> removeSnapshot(URI consistencyGroupId, URI snapshotId) {
        return execute(new DeactivateConsistencyGroupSnapshot(consistencyGroupId, snapshotId));
    }
}