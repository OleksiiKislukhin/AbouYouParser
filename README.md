# AboutYouParser
## This parser extracts product information from the following website:

https://www.aboutyou.de/c/maenner/bekleidung-20290

### The following open source libraries was used:

* JSOUP v.1.13.1 to get data from web page in JSON format;
* GSON v.2.2.4 to parse and save data in JSON;

The URL for parsing was obtained after examining the Network tab with XHR filter in Chrome, while scrolling down.
Parsing algorithm is based on iterating over the index in URL. Number of subpages for full page content is 5.

Was used simple multi-threading. Thread count is 2.

The extracted offers will be written into a JSON file "output.json" in the same folder where jar file is. 

### A short summary with the following information will be printed to stdout:

*   Amount of triggered HTTP requests
*   Amount of extracted products

