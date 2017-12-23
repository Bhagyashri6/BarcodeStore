package com.atminfotech.barcodestorenew;

class AppsList {

    public int icon;
    public String title;

    private static final AppsList ourInstance = new AppsList();

    static AppsList getInstance() {
        return ourInstance;
    }

    private AppsList(){
        super();
    }

    public AppsList(int icon, String title) {
        super();
        this.icon = icon;
        this.title = title;
    }
}
