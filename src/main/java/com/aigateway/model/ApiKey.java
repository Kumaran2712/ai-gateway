package com.aigateway.model;
import jakarta.persistence.*;


@Entity
@Table(name = "api_keys")
public class ApiKey {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique=true, nullable=false)
    private String key;

    @Column(nullable=false)
    private String tenant;


    private Integer quotaLimit;
    private Integer usedTokens;

    public ApiKey(){}

    public ApiKey(String key, String tenant, Integer quotaLimit, Integer usedTokens) {
        this.key = key;
        this.tenant = tenant;
        this.quotaLimit = quotaLimit;
        this.usedTokens = usedTokens;
    }

    public Long getId() {
        return id;
    }

    public String getKey(){
        return key;
    }

    public String getTenant(){
        return tenant;
    }

    public Integer getQuotaLimit(){
        return quotaLimit;
    }

    public Integer getUsedTokens(){
        return usedTokens;
    }

    public void setUsedTokens(Integer usedTokens){
        this.usedTokens = usedTokens;
    }


    
}
