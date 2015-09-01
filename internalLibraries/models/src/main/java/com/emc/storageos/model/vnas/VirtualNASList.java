/*
 * Copyright (c) 2015 EMC Corporation
 * All Rights Reserved
 */
package com.emc.storageos.model.vnas;

import com.emc.storageos.model.NamedRelatedResourceRep;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 * Class represents a return type that returns the id and self link for a
 * list of storage ports.
 */
@XmlRootElement(name = "vnas_servers")
public class VirtualNASList {
    private List<NamedRelatedResourceRep> vnasservers;

    public VirtualNASList() {
    }

    public VirtualNASList(List<NamedRelatedResourceRep> vnasservers) {
        this.vnasservers = vnasservers;
    }

    /**o
     * List of Storage ports. A Storage port represents a
     * port of a storage device.
     * 
     * @valid none
     */
    @XmlElement(name = "vnas_server")
    public List<NamedRelatedResourceRep> getVNASServers() {
        if (vnasservers == null) {
            vnasservers = new ArrayList<NamedRelatedResourceRep>();
        }
        return vnasservers;
    }

    public void setPorts(List<NamedRelatedResourceRep> vnasservers) {
        this.vnasservers = vnasservers;
    }
}