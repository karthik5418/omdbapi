
package com.karthik.demo.app.omdb.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

@SuppressWarnings("unused")
public class FeedModel {

    @SerializedName("message")
    private String mMessage;
    @SerializedName("response")
    private List<Response> mResponse;
    @SerializedName("status")
    private String mStatus;

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public List<Response> getResponse() {
        return mResponse;
    }

    public void setResponse(List<Response> response) {
        mResponse = response;
    }

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String status) {
        mStatus = status;
    }

    public class Response {

        @SerializedName("comments_count")
        private String mCommentsCount;
        @SerializedName("created_at")
        private String mCreatedAt;
        @SerializedName("created_by_user_id")
        private String mCreatedByUserId;
        @SerializedName("first_name")
        private String mFirstName;
        @SerializedName("is_my_like")
        private String mIsMyLike;
        @SerializedName("last_name")
        private String mLastName;
        @SerializedName("location_id")
        private String mLocationId;
        @SerializedName("location_name")
        private String mLocationName;
        @SerializedName("photo_url")
        private String mPhotoUrl;
        @SerializedName("post_id")
        private String mPostId;
        @SerializedName("post_message")
        private String mPostMessage;
        @SerializedName("total_like_count")
        private String mTotalLikeCount;
        @SerializedName("user_photo")
        private String mUserPhoto;

        public String getCommentsCount() {
            return mCommentsCount;
        }

        public void setCommentsCount(String comments_count) {
            mCommentsCount = comments_count;
        }

        public String getCreatedAt() {
            return mCreatedAt;
        }

        public void setCreatedAt(String created_at) {
            mCreatedAt = created_at;
        }

        public String getCreatedByUserId() {
            return mCreatedByUserId;
        }

        public void setCreatedByUserId(String created_by_user_id) {
            mCreatedByUserId = created_by_user_id;
        }

        public String getFirstName() {
            return mFirstName;
        }

        public void setFirstName(String first_name) {
            mFirstName = first_name;
        }

        public String getIsMyLike() {
            return mIsMyLike;
        }

        public void setIsMyLike(String is_my_like) {
            mIsMyLike = is_my_like;
        }

        public String getLastName() {
            return mLastName;
        }

        public void setLastName(String last_name) {
            mLastName = last_name;
        }

        public String getLocationId() {
            return mLocationId;
        }

        public void setLocationId(String location_id) {
            mLocationId = location_id;
        }

        public String getLocationName() {
            return mLocationName;
        }

        public void setLocationName(String location_name) {
            mLocationName = location_name;
        }

        public String getPhotoUrl() {
            return mPhotoUrl;
        }

        public void setPhotoUrl(String photo_url) {
            mPhotoUrl = photo_url;
        }

        public String getPostId() {
            return mPostId;
        }

        public void setPostId(String post_id) {
            mPostId = post_id;
        }

        public String getPostMessage() {
            return mPostMessage;
        }

        public void setPostMessage(String post_message) {
            mPostMessage = post_message;
        }

        public String getTotalLikeCount() {
            return mTotalLikeCount;
        }

        public void setTotalLikeCount(String total_like_count) {
            mTotalLikeCount = total_like_count;
        }

        public String getUserPhoto() {
            return mUserPhoto;
        }

        public void setUserPhoto(String user_photo) {
            mUserPhoto = user_photo;
        }

    }
}
