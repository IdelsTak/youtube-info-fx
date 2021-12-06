# YouTubeInfoFX

JavaFX application to extract information from YouTube API v3.

It does the following:

1. Lists YouTube categories with the details: 
   - Category ID
   - Category Name
2. Lists the most watched `n` videos in a country—e.g., US, Canada, etc.—with the details:
   - Channel ID
   - Number of views
   - Date published
3. Lists the most watched `n` videos in a country under a given category, with the details:
   - Channel ID
   - Number of views
   - Date published
4. Lists the top subscribed channels for a given region—e.g., US, Canada, etc.: —with the details:
   - User name
   - Channel ID
   - Number of subscribers
5. Lists the top subscribed channels for a given region—e.g., US, Canada, etc.—and category ID; with the details:
   - User name
   - Channel ID
   - Number of subscribers
   - Number of views
6. Lists the most watched YouTube channels per category for a given region—with the details:
   - User name
   - Channel ID
   - Number of subscribers
7. Lists the top `n` trending channels (while using `Chart = MostPopular`) for a given region—with the details:
   - User name
   - Channel ID
   - Number of subscribers
8. Lists live streaming info
   - Lists live streaming channel for a given channel/user name.
   - Lists live streaming channels for [top 100 news channels](https://hypeauditor.com/top-youtube-news-politics-united-states/) in the USA

