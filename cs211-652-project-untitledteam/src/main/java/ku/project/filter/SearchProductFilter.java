package ku.project.filter;

import ku.project.models.account.Account;
import ku.project.models.account.AccountList;
import ku.project.models.products.Product;
import ku.project.models.user.Borrow;
import ku.project.services.AccountDataSource;
import ku.project.services.DataSource;

import java.util.List;

public class SearchProductFilter implements ProductFilterer{
    private String search;
    private DataSource<AccountList> accountListDataSource = new AccountDataSource();
    private AccountList accountList = accountListDataSource.readData();
    public SearchProductFilter(String search) {
        this.search = search;
    }
    @Override
    public boolean filter(Product product) {
        if (search.equals("")) return true;
        Account account=accountList.searchAccountByUsername(product.getHolder());
        return KMPSearch(search.toLowerCase(), product.getProductName().toLowerCase(),product.getLocation().toLowerCase(),
                product.getCategory().toLowerCase(),account.getName().toLowerCase());
    }


    /**
     * Searches for the pattern `pat` in the text `txt` using the Knuth-Morris-Pratt algorithm.
     *
     * @param pat the pattern to search for
     * @param txt the text to search in
     * @return true if the pattern is found in the text, false otherwise
     */
    boolean KMPSearch(String pat, String txt,String textLocation,String textCategory, String textHolder) {
        int patternLength = pat.length();
        int textLength = txt.length();
        int textLocationLength = textLocation.length();
        int textCategoryLength = textCategory.length();
        int textHolderLength = textHolder.length();

        // Compute the lps[] array
        int[] lps = computeLPSArray(pat, patternLength);

        int patternIndex = 0; // Index for pat[]
        int textIndex = 0; // Index for txt[]
        while (textIndex < textLength) {
            if (pat.charAt(patternIndex) == txt.charAt(textIndex)) {
                patternIndex++;
                textIndex++;
            }
            if (patternIndex == patternLength) {
                return true;
            } else if (textIndex < textLength && pat.charAt(patternIndex) != txt.charAt(textIndex)) {
                // Mismatch after patternIndex matches
                if (patternIndex != 0) {
                    patternIndex = lps[patternIndex - 1];
                } else {
                    textIndex = textIndex + 1;
                }
            }
        }
        patternIndex = 0; // Index for pat[]
        textIndex = 0; // Index for txt[]
        while (textIndex < textLocationLength) {
            if (pat.charAt(patternIndex) == textLocation.charAt(textIndex)) {
                patternIndex++;
                textIndex++;
            }
            if (patternIndex == patternLength) {
                return true;
            } else if (textIndex < textLocationLength && pat.charAt(patternIndex) != textLocation.charAt(textIndex)) {
                // Mismatch after patternIndex matches
                if (patternIndex != 0) {
                    patternIndex = lps[patternIndex - 1];
                } else {
                    textIndex = textIndex + 1;
                }
            }
        }
        patternIndex = 0; // Index for pat[]
        textIndex = 0; // Index for txt[]
        while (textIndex < textCategoryLength) {
            if (pat.charAt(patternIndex) == textCategory.charAt(textIndex)) {
                patternIndex++;
                textIndex++;
            }
            if (patternIndex == patternLength) {
                return true;
            } else if (textIndex < textCategoryLength && pat.charAt(patternIndex) != textCategory.charAt(textIndex)) {
                // Mismatch after patternIndex matches
                if (patternIndex != 0) {
                    patternIndex = lps[patternIndex - 1];
                } else {
                    textIndex = textIndex + 1;
                }
            }
        }
        patternIndex = 0; // Index for pat[]
        textIndex = 0; // Index for txt[]
        while (textIndex < textHolderLength) {
            if (pat.charAt(patternIndex) == textHolder.charAt(textIndex)) {
                patternIndex++;
                textIndex++;
            }
            if (patternIndex == patternLength) {
                return true;
            } else if (textIndex < textHolderLength && pat.charAt(patternIndex) != textHolder.charAt(textIndex)) {
                // Mismatch after patternIndex matches
                if (patternIndex != 0) {
                    patternIndex = lps[patternIndex - 1];
                } else {
                    textIndex = textIndex + 1;
                }
            }
        }
        return false;
    }

    /**
     * Computes the lps[] array for the given pattern.
     *
     * @param pat the pattern to compute the lps[] array for
     * @param patternLength the length of the pattern
     * @return the lps[] array
     */
    int[] computeLPSArray(String pat, int patternLength) {
        int[] lps = new int[patternLength];
        int len = 0; // Length of the previous longest prefix suffix
        int i = 1;

        lps[0] = 0; // lps[0] is always 0
        while (i < patternLength) {
            if (pat.charAt(i) == pat.charAt(len)) {
                len++;
                lps[i] = len;
                i++;
            } else {
                if (len != 0) {
                    len = lps[len - 1];
                } else {
                    lps[i] = len;
                    i++;
                }
            }
        }
        return lps;
    }

}