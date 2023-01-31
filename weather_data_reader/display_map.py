
'''
Briggs Twitchell
CS5001
Dr. G
4/1/2022
Project #8: Yearly Precipitation in Portland with Exception Handling
'''
import folium
import sys

POSSIBLE_CITIES_DICT = {"BIDDEFORD":[43.4926, 70.4534],'BUXTON':[43.6379, 70.5189],'CAPE ELIZABETH':[43.5637, 70.2000],'CHEBEAGUE ISLAND':[43.7417, 70.1075],'CLIFF ISLAND':[43.7041, 70.0997],
                'CUMBERLAND':[43.7966, 70.2585],'FREEPORT':[43.8570, 70.1031],'GORHAM':[43.6795, 70.4442],'GRAY':[43.8856, 70.3317],'LONG ISLAND':[43.6842, 70.1712],'NORTH WINDHAM':[43.8342, 70.4384],
                'PEAKS ISLAND':[43.6605, 70.1907],'PORTLAND':[43.6591, 70.2568],'POWNAL':[43.9133, 70.1804],'SACO':[43.5009, 70.4428],'SCARBOROUGH':[43.5902, 70.3345],
                'SOUTH PORTLAND':[43.6415, 70.2409],'STANDISH':[43.7356, 70.5509],'WEST FALMOUTH':[43.743534,70.260178],'WESTBROOK':[43.6770, 70.3712],'YARMOUTH':[43.8006, 70.1867]}

POSSIBLE_CITIES_LIST = ["BIDDEFORD",'BUXTON','CAPE ELIZABETH','CHEBEAGUE ISLAND','CLIFF ISLAND','CUMBERLAND','FREEPORT','GORHAM','GRAY','LONG ISLAND','NORTH WINDHAM',
                    'PEAKS ISLAND','PORTLAND','POWNAL','SACO','SCARBOROUGH','SOUTH PORTLAND','STANDISH','WEST FALMOUTH','WESTBROOK','YARMOUTH']

'''
Function: reads precipitation data from a .txt file and formats it to input into the display map function
Parameters: text file name
Returns: list containing cities, precipitation data, start year, & end year
'''
def extract_format(file):
    #opens file
    file_han = open(file,'r')
    file_contents = file_han.read()
    file_contents = file_contents.split("\n")

    city_list = []
    precip_list = []

    #add city to a list if it's in the file
    for line in file_contents:
        for city in POSSIBLE_CITIES_LIST:
            if city in line.upper():
                city_list.append(city)

    #creates a list within a list with the pairs of the dates and precipitation values
    for line in file_contents:
        if line[0:4].isnumeric():
            sub_list = line.split(": ")
            precip_list.append(sub_list)

    #obtains the start and end year
    start_year = precip_list[0][0]
    end_year = precip_list[-1][0]

    #closes the file and returns back the data into a list
    file_han.close()
    city_data = [city_list,precip_list,start_year,end_year]
    return city_data

'''
Function: creates a .html file to display a map containing icons located at the cities' coordinates with their precip data
Parameters: list of cities, list of precipitation data, beg_year, end_year
'''
def create_map(city_list:list,precip_data:list,beg_year:int,end_year:int):
    
    #create map object and set coordinates to first city in the city_list
    city_1 = city_list[0].upper()
    city_1_coord = POSSIBLE_CITIES_DICT.get(city_1)
    maine_map = folium.Map(location=[city_1_coord[0],-city_1_coord[1]],zoom_start=11)

    #general message for click icon preview
    tooltip = 'Click for annual average rainfall data'

    #for each city in the city list, creates an icon at its coordinate value and inputs the years and precipitation data into the icon
    for i in range(len(city_list)):
        message = (f'<strong>Average&nbspDaily&nbspPrecipitation: ')
        data_per_city = int(len(precip_data) / len(city_list))
        for r in range(data_per_city):
            message = message + f"{precip_data[r+(i*data_per_city)][0]}:&nbsp{precip_data[r+(i*data_per_city)][1]} in.<br>"
        message = message + '</strong>'
        
        coord = POSSIBLE_CITIES_DICT.get(city_list[i].upper())     
        
        folium.Marker([coord[0],-coord[1]],
                    popup=message,
                    tooltip=tooltip,
                    icon=folium.Icon(icon='cloud')).add_to(maine_map)

    #generate map html file
    save_file = input("\nWhat would you like to name your .html map file? (make sure it ends in '.html')\n\t> ")
    maine_map.save(save_file)

def main(file = sys.argv):
    if len(file) <2:
        file = input("\nInput the average precipitation .txt file you'd like to create a map for\n\t> ")
    else:
        file = file[1]

    city_data = extract_format(file)
    create_map(city_data[0],city_data[1],city_data[2],city_data[3])


main()