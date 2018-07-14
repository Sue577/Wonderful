package com.zucc.wsq.a31501284.wonderfulwsq.litepalData;
import org.litepal.crud.DataSupport;

import static android.R.attr.id;

/**
 * Created by dell on 2018/7/14.
 */

public class FinanceRecord extends DataSupport{
    private String userId;
    private int financeTypeId;//金额的正负
    private String financeTypeImage;
    private String financeTypeName;
    private double financePrice;
    private String financeTime;

    public FinanceRecord(){ }

    public FinanceRecord(String userId,int typeId, String typeImage, String typeName, double price, String time){
        this.userId = userId;
        this.financeTypeId=typeId;
        this.financeTypeImage = typeImage;
        this.financeTypeName = typeName;
        this.financePrice = price;
        this.financeTime = time;
    }
    public FinanceRecord( String typeImage, String typeName, double price, String time){
        this.financeTypeImage = typeImage;
        this.financeTypeName = typeName;
        this.financePrice = price;
        this.financeTime = time;
    }

    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getFinanceTypeId() {
        return financeTypeId;
    }
    public void setFinanceTypeId(int financeTypeId) {
        this.financeTypeId = financeTypeId;
    }

    public String getFinanceTypeImage() {
        return financeTypeImage;
    }
    public void setFinanceTypeImage(String financeTypeImage) {
        this.financeTypeImage = financeTypeImage;
    }

    public String getFinanceTypeName() {
        return financeTypeName;
    }
    public void setFinanceTypeName(String financeTypeName) {
        this.financeTypeName = financeTypeName;
    }

    public double getFinancePrice() {
        return financePrice;
    }
    public void setFinancePrice(double financePrice) {
        this.financePrice = financePrice;
    }

    public String getFinanceTime() {
        return financeTime;
    }
    public void setFinanceTime(String financeTime) {
        this.financeTime = financeTime;
    }
}
