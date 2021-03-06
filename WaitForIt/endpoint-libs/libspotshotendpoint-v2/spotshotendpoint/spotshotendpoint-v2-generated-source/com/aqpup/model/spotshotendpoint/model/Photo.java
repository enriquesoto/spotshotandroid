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
 * Model definition for Photo.
 *
 * <p> This is the Java data model class that specifies how to parse/serialize into the JSON that is
 * transmitted over HTTP when working with the spotshotendpoint. For a detailed explanation see:
 * <a href="http://code.google.com/p/google-http-java-client/wiki/JSON">http://code.google.com/p/google-http-java-client/wiki/JSON</a>
 * </p>
 *
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public final class Photo extends com.google.api.client.json.GenericJson {

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String description;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private GeoPt geopt;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String id;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String path2photo;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.util.List<Comment> photoComments;

  static {
    // hack to force ProGuard to consider Comment used, since otherwise it would be stripped out
    // see http://code.google.com/p/google-api-java-client/issues/detail?id=528
    com.google.api.client.util.Data.nullOf(Comment.class);
  }

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Float photoRate;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.util.List<Rating> photoRates;

  static {
    // hack to force ProGuard to consider Rating used, since otherwise it would be stripped out
    // see http://code.google.com/p/google-api-java-client/issues/detail?id=528
    com.google.api.client.util.Data.nullOf(Rating.class);
  }

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Double popularity;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Integer state;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private com.google.api.client.util.DateTime uploadDate;

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getDescription() {
    return description;
  }

  /**
   * @param description description or {@code null} for none
   */
  public Photo setDescription(java.lang.String description) {
    this.description = description;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public GeoPt getGeopt() {
    return geopt;
  }

  /**
   * @param geopt geopt or {@code null} for none
   */
  public Photo setGeopt(GeoPt geopt) {
    this.geopt = geopt;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getId() {
    return id;
  }

  /**
   * @param id id or {@code null} for none
   */
  public Photo setId(java.lang.String id) {
    this.id = id;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getPath2photo() {
    return path2photo;
  }

  /**
   * @param path2photo path2photo or {@code null} for none
   */
  public Photo setPath2photo(java.lang.String path2photo) {
    this.path2photo = path2photo;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.util.List<Comment> getPhotoComments() {
    return photoComments;
  }

  /**
   * @param photoComments photoComments or {@code null} for none
   */
  public Photo setPhotoComments(java.util.List<Comment> photoComments) {
    this.photoComments = photoComments;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Float getPhotoRate() {
    return photoRate;
  }

  /**
   * @param photoRate photoRate or {@code null} for none
   */
  public Photo setPhotoRate(java.lang.Float photoRate) {
    this.photoRate = photoRate;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.util.List<Rating> getPhotoRates() {
    return photoRates;
  }

  /**
   * @param photoRates photoRates or {@code null} for none
   */
  public Photo setPhotoRates(java.util.List<Rating> photoRates) {
    this.photoRates = photoRates;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Double getPopularity() {
    return popularity;
  }

  /**
   * @param popularity popularity or {@code null} for none
   */
  public Photo setPopularity(java.lang.Double popularity) {
    this.popularity = popularity;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Integer getState() {
    return state;
  }

  /**
   * @param state state or {@code null} for none
   */
  public Photo setState(java.lang.Integer state) {
    this.state = state;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public com.google.api.client.util.DateTime getUploadDate() {
    return uploadDate;
  }

  /**
   * @param uploadDate uploadDate or {@code null} for none
   */
  public Photo setUploadDate(com.google.api.client.util.DateTime uploadDate) {
    this.uploadDate = uploadDate;
    return this;
  }

  @Override
  public Photo set(String fieldName, Object value) {
    return (Photo) super.set(fieldName, value);
  }

  @Override
  public Photo clone() {
    return (Photo) super.clone();
  }

}
