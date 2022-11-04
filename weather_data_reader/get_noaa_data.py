'''
Briggs Twitchell
CS5001
Dr. G
4/1/2022
Project #8: Yearly Precipitation in Portland with Exception Handling
'''

import os

#create class for FileFormatError
class FileFormatError(Exception):
    def __init__(self):
        self.message = "File in incorrect format"

POSSIBLE_CITIES = ["BIDDEFORD",'BUXTON','CAPE ELIZABETH','CHEBEAGUE ISLAND','CLIFF ISLAND','CUMBERLAND','FREEPORT','GORHAM','GRAY','LONG ISLAND','NORTH WINDHAM',
                    'PEAKS ISLAND','PORTLAND','POWNAL','SACO','SCARBOROUGH','SOUTH PORTLAND','STANDISH','WEST FALMOUTH','WESTBROOK','YARMOUTH']

'''
Function: calculates the average from a list of integers or floats
Parameters: num_list
Returns: float
'''
def get_avg(num_list):

    if not isinstance(num_list,list):
        raise TypeError(f"{num_list} must be a list")

    total = 0
    for n in num_list:
        total += n
    if len(num_list) == 0:
        return 0
    avg = total/len(num_list)
    return round(avg,3)

'''
Function: Pulls the precipitation data from a NOAA .csv file
Parameters: town name, beginning year, end year
Returns: list containing precipitation data, town name, and beg_year
'''
def get_precip_list(file,town_name,beg_year,end_year):

    #correctly formats town name (assumes function call didn't already account for this)
    town_name = '"'+town_name.upper()

    #opens user file
    file = open(file,'r')

    #creates empty list for number of years that the user specifies
    precip_data = []
    blank_count = 0
    for i in range(end_year+1 - beg_year):
        precip_data.append([])

    #reads the .csv file line by line and extracts precipitation data and inputs into a list if it's associated with the city and date ranges
    file_line_count = 0    
    for line in file:
        col = line.split(",")

        #checks that the first line in the '.csv' file has the correct headers
        if file_line_count == 0:
            file_line_count += 1
            if col[0] != '"STATION"' or col[1] != '"NAME"' or col[2]!= '"DATE"' or col[8] != '"PRCP"':
                print("\n'.csv' file not formatted correctly. Make sure data format matches direct download from https://www.NOAA.gov.\n")
                raise FileFormatError

        town = col[1].strip().upper()

        #resets loop if the town isn't the one specified by the user
        if not town.startswith(town_name):
            continue
        #if the precipitation column is blank, adds to the count of blanks
        if col[9] == '':
            blank_count += 1
            continue#col[9] = 0 #<-- included this in case blanks are considered to be 0 in. of precipitation for that day
        #else:
        #
        precip = float(col[9][1:-1])
        year = int(col[3][1:5])

        #skips year if it's beyond the year that the user specified
        if year >end_year:
            continue

        date_index = year - beg_year
        
        #adds the precipitation value to the positional value of the year its associated with
        precip_data[date_index].append(precip)
    
    #runs the get_avg function on all the lists in the precip_data and replaces them with their average
    for i in range(len(precip_data)):
        precip_data[i] = get_avg(precip_data[i])

    #consolidates relevant data into a single list        
    town_data = [precip_data,town_name[1:].capitalize(),beg_year]

    file.close()
    print(f'\nFile contains {blank_count} blank precipitation days.\n')

    return town_data

'''
Function: Writes the precipitation averages for a specified town and year to a .txt file
Parameters: precip_data: list, town: str, beg_year: int
Returns: Nothing
'''
def print_avgs(data:list, city:str, starting_year:int):

    if not isinstance(data,list):
        raise TypeError(f"First argument -- {data}  -- must be a list.")
    if not isinstance(city,str):
        raise TypeError(f"Second argument -- {city}  -- must be a str.")
    if not isinstance(starting_year,int):
        raise TypeError(f"Third argument -- {starting_year}  -- must be an int.")
    
    year = starting_year
    end_year = starting_year+len(data)-1

    save = input("Do you want to save this data to a new file?\n('Y' or 'N')\n\t> ")

    while save != 'Y' and save != 'N':
        save = input("**INVALID INPUT** - Do you want to save this data to a new file?\n('Y' or 'N')\n\t> ")

    if save == 'N':
        return

    out_file_name = input("What would you like to name your output file?\n\t> ")
    out_file = open(out_file_name,'a')
    out_file.write(f'Average Daily Rainfall in {city} from {starting_year} to {end_year}:')
    for i in range(len(data)):
        out_file.write(f'\n{year}: {data[i]}')
        year += 1
    out_file.write("\n\n")
    out_file.close()

'''
Function: driver function
Parameters: none
Returns: nothing
'''
def main():
    flag = True
    while flag == True:
        try:
            #Raise a ValueError if the file name does not end with .csv
            file = input("\nInput the file name for the NOAA data you'd like to analyze:\n\t> ")
            if file[-4:] != '.csv':
                print("\nPlease make sure the file is a '.csv' file.")
                raise FileFormatError

            #checks to see if file is in the current directory
            if not os.path.exists(file):
                raise FileNotFoundError("Please make sure the file you entered is in your current directory.")
            
            city = input("What city would you like to obtain average rainfall data for?\n\t> ")
            if city.upper() not in POSSIBLE_CITIES:
                print("\nUnfortunately the city you want to obtain data on is not available.\n")
                raise ValueError

            start_date = (input("Input starting year (any year from 2010 to 2021):\n\t> "))
            end_date = (input("Input ending year (any year from 2010 to 2021):\n\t> "))

            if start_date.isnumeric() and end_date.isnumeric():
                start_date = int(start_date)
                end_date = int(end_date)
            else:
                raise TypeError("Make sure you enter a numeric value between 2010 and 2021")
            
            city_data = get_precip_list(file, city,start_date,end_date)
            print_avgs(city_data[0],city_data[1],city_data[2])

            more = input("\nWould you like to obtain data for any other towns?\n('Y' or 'N')\n\t> ")
            while more != 'Y' and more != 'N':
                    more = input("**INVALID INPUT**\nWould you like to obtain data for any other cities?\n('Y' or 'N')\n\t> ")
            if more == 'N':
                print('\nexiting program\n')
                flag = False

        except TypeError as te:
            print("\nTypeError raised\n")
            print(te)
        except FileNotFoundError as fnfe:
            print('\nFileNotFoundError raised\n')
            print(fnfe)
        except FileFormatError as ffe:
            print("\nFileFormatError raised\n")
        except ValueError as ve:
            print('\nValueError raised\n')
        except Exception as e:
            print(e)
        
main()
    

