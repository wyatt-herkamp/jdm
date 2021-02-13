package me.kingtux.jdm.common;

import me.kingtux.mavenlibrary.Artifact;

public class JDMArtifact {
    private String groupID;
    private String artifactID;
    private String version;

    public JDMArtifact(String groupID, String artifactID, String version) {
        this.groupID = groupID;
        this.artifactID = artifactID;
        this.version = version;
    }

    public Artifact toMavenLibraryArtifact() {
        return new Artifact(groupID, artifactID, version);
    }

    public String getGroupID() {
        return groupID;
    }

    public String getArtifactID() {
        return artifactID;
    }

    public String getVersion() {
        return version;
    }
}
