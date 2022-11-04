This project parses through a .csv file of precipitation data to display a map summarizes annual rainfall for a series of years.

The get_noaa_data.py file reads through a .csv file from the National Oceanic and Atmospheric Administration (NOAA) and extracts the data related to precipitation. It then computes average precipitation for the year and sends a summary to a .txt file.

The display_map.py reads in text file generated and uses the information to generated a .html file to display an interactive map with the average precipitation volumes for the specified town (limited to towns in Maine).
