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
 * Model definition for User.
 *
 * <p> This is the Java data model class that specifies how to parse/serialize into the JSON that is
 * transmitted over HTTP when working with the spotshotendpoint. For a detailed explanation see:
 * <a href="http://code.google.com/p/google-http-java-client/wiki/JSON">http://code.google.com/p/google-http-java-client/wiki/JSON</a>
 * </p>
 *
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public final class User extends com.google.api.client.json.GenericJson {

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private com.google.api.client.util.DateTime birthday;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String email;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Boolean emailVerified;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Integer gender;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String gmail;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key @com.google.api.client.json.JsonString
  private java.lang.Long id;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private com.google.api.client.util.DateTime lastLogin;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String name;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Integer newUser;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String password;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String pathAvatar;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private PhoneNumber phone;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.util.List<Photo> photos;

  static {
    // hack to force ProGuard to consider Photo used, since otherwise it would be stripped out
    // see http://code.google.com/p/google-api-java-client/issues/detail?id=528
    com.google.api.client.util.Data.nullOf(Photo.class);
  }

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Integer popularity;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.util.List<Rating> ratedPhotos;

  static {
    // hack to force ProGuard to consider Rating used, since otherwise it would be stripped out
    // see http://code.google.com/p/google-api-java-client/issues/detail?id=528
    com.google.api.client.util.Data.nullOf(Rating.class);
  }

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String regId;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Boolean state;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String username;

  /**
   * @return value or {@code null} for none
   */
  public com.google.api.client.util.DateTime getBirthday() {
    return birthday;
  }

  /**
   * @param birthday birthday or {@code null} for none
   */
  public User setBirthday(com.google.api.client.util.DateTime birthday) {
    this.birthday = birthday;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getEmail() {
    return email;
  }

  /**
   * @param email email or {@code null} for none
   */
  public User setEmail(java.lang.String email) {
    this.email = email;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Boolean getEmailVerified() {
    return emailVerified;
  }

  /**
   * @param emailVerified emailVerified or {@code null} for none
   */
  public User setEmailVerified(java.lang.Boolean emailVerified) {
    this.emailVerified = emailVerified;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Integer getGender() {
    return gender;
  }

  /**
   * @param gender gender or {@code null} for none
   */
  public User setGender(java.lang.Integer gender) {
    this.gender = gender;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getGmail() {
    return gmail;
  }

  /**
   * @param gmail gmail or {@code null} for none
   */
  public User setGmail(java.lang.String gmail) {
    this.gmail = gmail;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Long getId() {
    return id;
  }

  /**
   * @param id id or {@code null} for none
   */
  public User setId(java.lang.Long id) {
    this.id = id;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public com.google.api.client.util.DateTime getLastLogin() {
    return lastLogin;
  }

  /**
   * @param lastLogin lastLogin or {@code null} for none
   */
  public User setLastLogin(com.google.api.client.util.DateTime lastLogin) {
    this.lastLogin = lastLogin;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getName() {
    return name;
  }

  /**
   * @param name name or {@code null} for none
   */
  public User setName(java.lang.String name) {
    this.name = name;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Integer getNewUser() {
    return newUser;
  }

  /**
   * @param newUser newUser or {@code null} for none
   */
  public User setNewUser(java.lang.Integer newUser) {
    this.newUser = newUser;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getPassword() {
    return password;
  }

  /**
   * @param password password or {@code null} for none
   */
  public User setPassword(java.lang.String password) {
    this.password = password;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getPathAvatar() {
    return pathAvatar;
  }

  /**
   * @param pathAvatar pathAvatar or {@code null} for none
   */
  public User setPathAvatar(java.lang.String pathAvatar) {
    this.pathAvatar = pathAvatar;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public PhoneNumber getPhone() {
    return phone;
  }

  /**
   * @param phone phone or {@code null} for none
   */
  public User setPhone(PhoneNumber phone) {
    this.phone = phone;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.util.List<Photo> getPhotos() {
    return photos;
  }

  /**
   * @param photos photos or {@code null} for none
   */
  public User setPhotos(java.util.List<Photo> photos) {
    this.photos = photos;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Integer getPopularity() {
    return popularity;
  }

  /**
   * @param popularity popularity or {@code null} for none
   */
  public User setPopularity(java.lang.Integer popularity) {
    this.popularity = popularity;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.util.List<Rating> getRatedPhotos() {
    return ratedPhotos;
  }

  /**
   * @param ratedPhotos ratedPhotos or {@code null} for none
   */
  public User setRatedPhotos(java.util.List<Rating> ratedPhotos) {
    this.ratedPhotos = ratedPhotos;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getRegId() {
    return regId;
  }

  /**
   * @param regId regId or {@code null} for none
   */
  public User setRegId(java.lang.String regId) {
    this.regId = regId;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Boolean getState() {
    return state;
  }

  /**
   * @param state state or {@code null} for none
   */
  public User setState(java.lang.Boolean state) {
    this.state = state;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getUsername() {
    return username;
  }

  /**
   * @param username username or {@code null} for none
   */
  public User setUsername(java.lang.String username) {
    this.username = username;
    return this;
  }

  @Override
  public User set(String fieldName, Object value) {
    return (User) super.set(fieldName, value);
  }

  @Override
  public User clone() {
    return (User) super.clone();
  }

}