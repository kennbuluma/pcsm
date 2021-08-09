package com.foreverdevelopers.m_daktari.data;

public class RemoteActions {
    public String baseUrl, countiesEp, facilitiesEp, servicesEp, doctorsEp;
    public RemoteActions(){}
    public RemoteActions(String baseUrl,
                         String countiesEp,
                         String facilitiesEp,
                         String servicesEp,
                         String doctorsEp){
        this.baseUrl = baseUrl;
        this.countiesEp = countiesEp;
        this.facilitiesEp = facilitiesEp;
        this.servicesEp = servicesEp;
        this.doctorsEp = doctorsEp;
    }
}
