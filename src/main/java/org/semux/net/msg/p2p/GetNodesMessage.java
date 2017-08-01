/*
 * Copyright (c) 2017 The Semux Developers
 *
 * Distributed under the MIT software license, see the accompanying file
 * LICENSE or https://opensource.org/licenses/mit-license.php
 */
package org.semux.net.msg.p2p;

import java.net.InetSocketAddress;
import java.util.HashSet;
import java.util.Set;

import org.semux.net.msg.Message;
import org.semux.net.msg.MessageCode;
import org.semux.utils.SimpleDecoder;
import org.semux.utils.SimpleEncoder;

public class GetNodesMessage extends Message {

    private Set<InetSocketAddress> nodes;

    /**
     * Create a GET_NODES message.
     * 
     * @param nodes
     */
    public GetNodesMessage(Set<InetSocketAddress> nodes) {
        super(MessageCode.GET_NODES, NodesMessage.class);

        this.nodes = nodes;

        SimpleEncoder enc = new SimpleEncoder();
        enc.writeInt(nodes.size());
        for (InetSocketAddress addr : nodes) {
            enc.writeString(addr.getAddress().getHostAddress());
            enc.writeInt(addr.getPort());
        }
        this.encoded = enc.toBytes();
    }

    /**
     * Parse a GET_NODES message from byte array.
     * 
     * @param encoded
     */
    public GetNodesMessage(byte[] encoded) {
        super(MessageCode.GET_NODES, NodesMessage.class);

        this.encoded = encoded;

        nodes = new HashSet<>();
        SimpleDecoder dec = new SimpleDecoder(encoded);
        int n = dec.readInt();
        for (int i = 0; i < n; i++) {
            String host = dec.readString();
            int port = dec.readInt();
            nodes.add(new InetSocketAddress(host, port));
        }
    }

    public Set<InetSocketAddress> getNodes() {
        return nodes;
    }

    @Override
    public String toString() {
        return "GetNodesMessage [# nodes =" + nodes.size() + "]";
    }
}
