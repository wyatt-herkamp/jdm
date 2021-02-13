package me.kingtux.jdm.common;

import me.kingtux.mavenlibrary.Repository;

import java.util.List;

public class Report {
    private List<Repository> repositoryList;
    private List<JDMArtifact> artifactList;

    public Report(List<Repository> repositoryList, List<JDMArtifact> artifactList) {
        this.repositoryList = repositoryList;
        this.artifactList = artifactList;
    }

    public List<Repository> getRepositoryList() {
        return repositoryList;
    }

    public List<JDMArtifact> getArtifactList() {
        return artifactList;
    }
}
