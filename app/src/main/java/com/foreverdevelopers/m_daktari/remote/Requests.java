package com.foreverdevelopers.m_daktari.remote;

import com.foreverdevelopers.m_daktari.data.HttpClient;

public class Requests {
    private final String baseUrl,
            doctorsAll, doctorsByFacility, doctorsByService,
            countiesAll, countiesByService, countiesByFacility,
            facilitiesAll, facilitiesByCounty,
            serviceAll, serviceByCounty, serviceByFacility;
    private final HttpClient httpClient;
    private final Remote remote;
    public Requests(HttpClient httpClient,
                    Remote remote,
                    String baseUrl,
                    String doctorsAll,
                    String doctorsByFacility,
                    String doctorsByService,
                    String countiesAll,
                    String countiesByService,
                    String countiesByFacility,
                    String facilitiesAll,
                    String facilitiesByCounty,
                    String serviceAll,
                    String serviceByCounty,
                    String serviceByFacility){
        this.httpClient = httpClient;
        this.remote = remote;
        this.baseUrl = baseUrl;
        this.doctorsAll = doctorsAll;
        this.doctorsByFacility = doctorsByFacility;
        this.doctorsByService = doctorsByService;
        this.countiesAll = countiesAll;
        this.countiesByService = countiesByService;
        this.countiesByFacility = countiesByFacility;
        this.facilitiesAll = facilitiesAll;
        this.facilitiesByCounty = facilitiesByCounty;
        this.serviceAll = serviceAll;
        this.serviceByCounty = serviceByCounty;
        this.serviceByFacility = serviceByFacility;
    }
    public class Counties{
        public void all(){
            String url = baseUrl+countiesAll;
        }
        public void byFacility(String facility){
            String url = baseUrl+countiesByFacility+"/"+facility;
        }
        public void byService(String service){
            String url = baseUrl+countiesByService+"/"+service;
        }
    }
    public class Facilities{
        public void all(){
            String url = baseUrl+facilitiesAll;
        }
        public void byCounty(String county){
            String url = baseUrl+facilitiesByCounty+"/"+county;
        }
    }
    public class Services{
        public void all(){
            String url = baseUrl+serviceAll;
        }
        public void byFacility(String facility){
            String url = baseUrl+serviceByFacility+"/"+facility;
        }
        public void byCounty(String county){
            String url = baseUrl+serviceByCounty+"/"+county;
        }
    }
    public class Doctors{
        public void all(){
            String url = baseUrl+doctorsAll;
        }
        public void byFacility(String facility){
            String url = baseUrl+doctorsByFacility+"/"+facility;
        }
        public void byService(String service){
            String url = baseUrl+doctorsByService+"/"+service;
        }
    }
}
