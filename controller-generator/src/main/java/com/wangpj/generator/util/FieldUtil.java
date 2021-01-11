package com.wangpj.generator.util;

public class FieldUtil {

    /**
     * 从类名获得数据库表名
     * @param domainName
     * @return
     */
    public static String change2DatabaseTableName(String domainName)
    {
        StringBuilder stringBuilder=new StringBuilder();
        domainName=domainName.trim();
        domainName=domainName.substring(0,1).toLowerCase()+domainName.substring(1);
        for (int i = 0; i <domainName.length() ; i++) {
            char c = domainName.charAt(i);
            if (Character.isLowerCase(c))
            {
                stringBuilder.append(c);
            }else
            {
                stringBuilder.append("_").append(Character.toLowerCase(c));
            }
        }
        return stringBuilder.toString();
    }

}
