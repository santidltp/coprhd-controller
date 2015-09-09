/*
 * Copyright (c) 2012 EMC Corporation
 * All Rights Reserved
 */
package com.emc.storageos.api.service.impl.resource;

import java.net.URI;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.emc.storageos.api.service.impl.resource.snapshot.BlockSnapshotSessionManager;
import com.emc.storageos.db.client.model.BlockSnapshotSession;
import com.emc.storageos.db.client.model.Project;
import com.emc.storageos.model.BulkIdParam;
import com.emc.storageos.model.ResourceTypeEnum;
import com.emc.storageos.model.TaskResourceRep;
import com.emc.storageos.model.block.BlockSnapshotSessionBulkRep;
import com.emc.storageos.model.block.BlockSnapshotSessionRestRep;
import com.emc.storageos.model.block.SnapshotSessionLinkTargetsParam;
import com.emc.storageos.model.block.SnapshotSessionRelinkTargetsParam;
import com.emc.storageos.model.block.SnapshotSessionUnlinkTargetsParam;
import com.emc.storageos.security.authorization.ACL;
import com.emc.storageos.security.authorization.CheckPermission;
import com.emc.storageos.security.authorization.DefaultPermissions;
import com.emc.storageos.security.authorization.Role;

/**
 * Service class that implements APIs on instances of BlockSnapshotSession.
 */
@Path("/block/snapshot-sessions")
@DefaultPermissions(readRoles = { Role.SYSTEM_MONITOR, Role.TENANT_ADMIN }, readAcls = { ACL.ANY },
        writeRoles = { Role.TENANT_ADMIN }, writeAcls = { ACL.ANY })
public class BlockSnapshotSessionService extends TaskResourceService {

    /**
     * The method implements the API to create and link new target
     * volumes to an existing BlockSnapshotSession instance.
     * 
     * @brief Link target volumes to a snapshot session.
     * 
     * @prereq The block snapshot session has been created and the maximum
     *         number of targets has not already been linked to the snapshot sessions
     *         for the source object.
     * 
     * @param id The URI of the BlockSnapshotSession instance to which the
     *            new targets will be linked.
     * @param param The linked target information.
     * 
     * @return A TaskResourceRep representing the snapshot session task.
     */
    @POST
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Path("/{id}/link-targets")
    @CheckPermission(roles = { Role.TENANT_ADMIN }, acls = { ACL.ANY })
    public TaskResourceRep linkTargetVolumes(@PathParam("id") URI id, SnapshotSessionLinkTargetsParam param) {
        return getSnapshotSessionManager().linkTargetVolumesToSnapshotSession(id, param);
    }

    /**
     * This method implements the API to re-link a target to either it's current
     * snapshot session or to a different snapshot session of the same source.
     * 
     * @brief Relink target volumes to snapshot sessions.
     * 
     * @prereq The target volumes are linked to a snapshot session of the same source object.
     * 
     * @param id The URI of the BlockSnapshotSession instance to which the
     *            the targets will be re-linked.
     * @param param The linked target information.
     * 
     * @return A TaskResourceRep representing the snapshot session task.
     */
    @POST
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Path("/{id}/relink-targets")
    @CheckPermission(roles = { Role.TENANT_ADMIN }, acls = { ACL.ANY })
    public TaskResourceRep relinkTargetVolumes(@PathParam("id") URI id, SnapshotSessionRelinkTargetsParam param) {
        return getSnapshotSessionManager().relinkTargetVolumesToSnapshotSession(id, param);
    }

    /**
     * The method implements the API to unlink target volumes from an existing
     * BlockSnapshotSession instance and optionally delete those target volumes.
     * 
     * @brief Unlink target volumes from a snapshot session.
     * 
     * @prereq A snapshot session is created and target volumes have previously
     *         been linked to that snapshot session.
     * 
     * @param id The URI of the BlockSnapshotSession instance to which the targets are linked.
     * @param param The linked target information.
     * 
     * @return A TaskResourceRep representing the snapshot session task.
     */
    @POST
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Path("/{id}/unlink-targets")
    @CheckPermission(roles = { Role.TENANT_ADMIN }, acls = { ACL.ANY })
    public TaskResourceRep unlinkTargetVolumesForSession(@PathParam("id") URI id, SnapshotSessionUnlinkTargetsParam param) {
        return getSnapshotSessionManager().unlinkTargetVolumesFromSnapshotSession(id, param);
    }

    /**
     * Restores the data on the array snapshot point-in-time copy represented by the
     * BlockSnapshotSession instance with the passed id, to the snapshot session source
     * object.
     * 
     * @brief Restore snapshot session to source
     * 
     * @prereq None
     * 
     * @param id The URI of the BlockSnapshotSession instance to be restored.
     * 
     * @return TaskResourceRep representing the snapshot session task.
     */
    @POST
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Path("/{id}/restore")
    @CheckPermission(roles = { Role.TENANT_ADMIN }, acls = { ACL.ANY })
    public TaskResourceRep restoreSnapshotSession(@PathParam("id") URI id) {
        return getSnapshotSessionManager().restoreSnapshotSession(id);
    }

    /**
     * Deletes the BlockSnapshotSession instance with the passed id.
     * 
     * @brief Delete a block snapshot session.
     * 
     * @prereq The block snapshot session has no linked target volumes.
     * 
     * @param id The URI of the BlockSnapshotSession instance to be deleted.
     * 
     * @return TaskResourceRep representing the snapshot session task.
     */
    @POST
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Path("/{id}/deactivate")
    @CheckPermission(roles = { Role.TENANT_ADMIN }, acls = { ACL.ANY })
    public TaskResourceRep deactivateSnapshotSession(@PathParam("id") URI id) {
        return getSnapshotSessionManager().deleteSnapshotSession(id);
    }

    /**
     * Get the details of the BlockSnapshotSession instance with the passed id.
     * 
     * @brief Get snapshot session details.
     * 
     * @prereq none
     * 
     * @param id The URI of the BlockSnapshotSession instance.
     * 
     * @return BlockSnapshotSessionRestRep specifying the snapshot session details.
     */
    @GET
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Path("/{id}")
    @CheckPermission(roles = { Role.SYSTEM_MONITOR, Role.TENANT_ADMIN }, acls = { ACL.ANY })
    public BlockSnapshotSessionRestRep getSnapshotSession(@PathParam("id") URI id) {
        return getSnapshotSessionManager().getSnapshotSession(id);
    }

    /**
     * Gets the details of the BlockSnapshotSession instances with the ids
     * specified in the passed data.
     * 
     * @brief Gets details for requested block snapshot sessions.
     * 
     * @prereq none
     * 
     * @param param The ids of the desired BlockSnapshotSession instances.
     * 
     * @return A BlockSnapshotSessionBulkRep with the snapshot session details.
     */
    @POST
    @Path("/bulk")
    @Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Override
    public BlockSnapshotSessionBulkRep getBulkResources(BulkIdParam param) {
        return (BlockSnapshotSessionBulkRep) super.getBulkResources(param);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected BlockSnapshotSession queryResource(URI id) {
        ArgValidator.checkUri(id);
        BlockSnapshotSession blockSnapshotSession = _permissionsHelper.getObjectById(id, BlockSnapshotSession.class);
        ArgValidator.checkEntityNotNull(blockSnapshotSession, id, isIdEmbeddedInURL(id));
        return blockSnapshotSession;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected URI getTenantOwner(URI id) {
        BlockSnapshotSession snapshotSession = queryResource(id);
        URI projectUri = snapshotSession.getProject().getURI();
        ArgValidator.checkUri(projectUri);
        Project project = _permissionsHelper.getObjectById(projectUri, Project.class);
        ArgValidator.checkEntityNotNull(project, projectUri, isIdEmbeddedInURL(projectUri));
        return project.getTenantOrg().getURI();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected ResourceTypeEnum getResourceType() {
        return ResourceTypeEnum.BLOCK_SNAPSHOT_SESSION;
    }

    /**
     * Creates and returns an instance of the block snapshot session manager to handle
     * a snapshot session requests.
     * 
     * @return BlockSnapshotSessionManager
     */
    private BlockSnapshotSessionManager getSnapshotSessionManager() {
        BlockSnapshotSessionManager snapshotSessionManager = new BlockSnapshotSessionManager(_dbClient,
                _permissionsHelper, _auditMgr, _coordinator, sc, uriInfo, _request);
        return snapshotSessionManager;
    }
}