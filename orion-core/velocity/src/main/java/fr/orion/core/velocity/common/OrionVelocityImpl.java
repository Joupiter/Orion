package fr.orion.core.velocity.common;

import fr.orion.api.utils.threading.MultiThreading;
import fr.orion.core.velocity.api.OrionVelocityApi;

public class OrionVelocityImpl extends OrionVelocityApi {

    @Override
    public void load() {
        registerRegistries();
        getDatabaseLoader().connect();
    }

    @Override
    public void unload() {
        getDatabaseLoader().disconnect();
        MultiThreading.shutdown();
    }

}