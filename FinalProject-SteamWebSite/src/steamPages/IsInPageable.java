package steamPages;

public interface IsInPageable {

	// interface that every page implements to check whether we are currently in the Page.
	public boolean isInPage();
	default void sup()
	{
		System.out.println();
	}
	
}
