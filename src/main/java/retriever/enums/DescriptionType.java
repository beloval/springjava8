package retriever.enums;

public enum DescriptionType {
    SHORT_DESCRIPTION("OnePageWebsiteCompanyShortDescription"),
    LONG_DESCRIPTION("OnePageWebsiteCompanyLongDescription"),
    MISSION("OnePageWebsiteMission");

    private String textLineType;

    DescriptionType(String textLineType) {
        this.textLineType = textLineType;
    }

    public String getTextLineType() {
        return this.textLineType;
    }
}