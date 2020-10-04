package com.jabirdeveloper.ircofhurapollz.model;

public class NotifikasiModel {

    private DataNotifikasi data;

    public NotifikasiModel(DataNotifikasi data) {
        this.data = data;
    }

    public DataNotifikasi getData() {
        return data;
    }

    public class DataNotifikasi {
        private String title, message, image;

        public DataNotifikasi(String title, String message, String image) {
            this.title = title;
            this.message = message;
            this.image = image;
        }

        public String getTitle() {
            return title;
        }

        public String getMessage() {
            return message;
        }

        public String getImage() {
            return image;
        }
    }
}
