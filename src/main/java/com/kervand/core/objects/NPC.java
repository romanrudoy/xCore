package com.kervand.core.objects;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class NPC {

    private final com.babijon.commons.npc.containers.interfaces.NPC npc;
    private final String command;
    private int cooldown;
    private String cooldownMessage;

}
