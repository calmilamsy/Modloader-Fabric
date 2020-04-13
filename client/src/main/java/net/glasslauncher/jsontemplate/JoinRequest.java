package net.glasslauncher.jsontemplate;

import lombok.Setter;

@Setter
public class JoinRequest {

    public JoinRequest(String accessToken, String selectedProfile, String serverId) {
        this.accessToken = accessToken;
        this.selectedProfile = selectedProfile;
        this.serverId = serverId;
    }

    private String accessToken;
    private String selectedProfile;
    private String serverId;
}
