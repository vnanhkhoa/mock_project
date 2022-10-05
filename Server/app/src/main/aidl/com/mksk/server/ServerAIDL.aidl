// ServerAIDL.aidl
package com.mksk.server;
import com.mksk.server.data.models.Employee;
import com.mksk.server.data.models.Table;
import com.mksk.server.data.models.Product;
import com.mksk.server.data.models.Revenue;
import com.mksk.server.data.models.RevenueDetail;

interface ServerAIDL {
    void sendAction(in Map<String,String> action);
    void sendRevenue(in Revenue revenue,in List<RevenueDetail> revenueDetails);
    void updateEmployee(in Employee e);
}