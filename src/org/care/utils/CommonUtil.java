/*
 * Copyright 2006-2018 (c) Care.com, Inc.
 * 1400 Main Street, Waltham, MA, 02451, U.S.A.
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of
 * Care.com, Inc. ("Confidential Information").  You shall not disclose
 * such Confidential Information and shall use it only in accordance with
 * the terms of an agreement between you and CZen.
 */
package org.care.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Logger;

import org.care.context.MyApplicationContext;

/**
 * Created 9/6/2018 11:04 PM
 */
public class CommonUtil {

  public static String getRedirectURL(String url) {
    return MyApplicationContext.get().getAppURI() + url;
  }

  public static String getHashedPassword(String plainText) {
    String saltPrefix = "1@123$%^%$%$5";
    String saltSuffix = "^%%^*()$asd";
    String saltedPassword = saltPrefix + plainText + saltSuffix;
    try {
      return new String(
          MessageDigest.getInstance("SHA-256").digest(saltedPassword.getBytes()));
    } catch (NoSuchAlgorithmException e) {
      Logger.getLogger(CommonUtil.class.getName()).severe("SHA256 alg not found in lib");
    }
    return null;
  }
}
