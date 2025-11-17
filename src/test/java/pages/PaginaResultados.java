package pages;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;
public class PaginaResultados extends PaginaBase{

    private final String resultItems = "//a[contains(@href,'/artist') or contains(@href,'/album') or contains(@data-testid,'tracklist-row')]";
    private final String firstArtistByText = "//a[contains(@href,'/artist')][.//span[contains(translate(., 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'),'coldplay')]]";

    public String getFirstResultText() {
        List<WebElement> items = findAll(By.id(resultItems));
        if (items.isEmpty()) return "";
        return items.get(0).getText();
    }

    public int getResultsCount() {
        return findAll(By.id(resultItems)).size();
    }

    public boolean isArtistPresent(String artistName) {
        try {
            String xpath = String.format("//a[contains(@href,'/artist')][.//span[contains(translate(., 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'),'%s')]]",
                    artistName.toLowerCase());
            WebElement el = find(By.id(xpath));
            return el != null;
        } catch (Exception e) {
            return false;
        }
    }
}
