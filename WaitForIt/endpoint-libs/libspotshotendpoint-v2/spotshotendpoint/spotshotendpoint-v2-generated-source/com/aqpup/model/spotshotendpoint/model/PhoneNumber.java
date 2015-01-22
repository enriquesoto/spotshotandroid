/*
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
/*
 * This code was generated by https://code.google.com/p/google-apis-client-generator/
 * (build: 2014-11-17 18:43:33 UTC)
 * on 2014-12-15 at 21:51:42 UTC 
 * Modify at your own risk.
 */

package com.aqpup.model.spotshotendpoint.model;

/**
 * Model definition for PhoneNumber.
 *
 * <p> This is the Java data model class that specifies how to parse/serialize into the JSON that is
 * transmitted over HTTP when working with the spotshotendpoint. For a detailed explanation see:
 * <a href="http://code.google.com/p/google-http-java-client/wiki/JSON">http://code.google.com/p/google-http-java-client/wiki/JSON</a>
 * </p>
 *
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public final class PhoneNumber extends com.google.api.client.json.GenericJson {

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String number;

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getNumber() {
    return number;
  }

  /**
   * @param number number or {@code null} for none
   */
  public PhoneNumber setNumber(java.lang.String number) {
    this.number = number;
    return this;
  }

  @Override
  public PhoneNumber set(String fieldName, Object value) {
    return (PhoneNumber) super.set(fieldName, value);
  }

  @Override
  public PhoneNumber clone() {
    return (PhoneNumber) super.clone();
  }

}
