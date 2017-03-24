package com.abbvie.productvisibility.util;

/**
 * Created by mohamfj on 12/07/2016.
 */
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;

import java.io.IOException;

public class HBaseConnector {
    public static void main(String[] args) throws IOException {

        Configuration config = HBaseConfiguration.create();


        HTable table = new HTable(config, "SVIS_DEV.SIMPLE");


        Scan s = new Scan();

        ResultScanner scanner = table.getScanner(s);
        try {

            for (Result rr = scanner.next(); rr != null; rr = scanner.next()) {

                System.out.println("Found row: " + rr);
            }


        } finally {

            scanner.close();
        }
    }
}