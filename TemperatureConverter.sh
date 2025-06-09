#$/bin/bash

units=("°C" "°F" "K")

echo "
Welcome to Bash Temperature Converter
1. Celsius (°C) 	2. Fahrenheit (°F)	 3. Kelvin (K)"

#Prompt units
while true; do
	read -p "Choose which unit to convert the temperature from: " fromUnit
	if [[ "$fromUnit" =~ ^[1-3]$ ]]; then
		break
	fi
done

while true; do
	read -p "Choose which unit to convert the temperature to: " toUnit
	if [[ "$toUnit" =~ ^[1-3]$ && "$fromUnit" != "$toUnit" ]]; then
		break
	fi
done

fromUnit=${units[$((fromUnit-1))]}
toUnit=${units[$((toUnit-1))]}

#Prompt value
while true; do
	read -p "Enter your temperature value in $fromUnit: " value
	if [[ "$value" =~ ^-?[0-9]*\.?[0-9]+$ ]]; then
		break
	fi
done

#conversions
result=$value

#convert all to Celsius
if [[ "$fromUnit" == "°F" ]]; then
    result=$(echo "scale=6; ($result - 32) * 5 / 9" | bc)
elif [[ "$fromUnit" == "K" ]]; then
	result=$(echo "scale=6; $result - 273.15" | bc)
fi

#convert to selected unit
if [[ "$toUnit" == "°F" ]]; then
    result=$(echo "scale=6; $result * 9 / 5 + 32" | bc)
elif [[ "$toUnit" == "K" ]]; then
    result=$(echo "scale=6; $result + 273.15" | bc)
fi

echo "$value$fromUnit is equal to: $result$toUnit"
