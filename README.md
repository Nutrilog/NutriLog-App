![GitHub Cards Preview](https://github.com/Nutrilog/NutriLog-App/blob/main/art/nutrilog-cover.png?raw=true)

![Build (Android)](https://github.com/Nutrilog/NutriLog-App/workflows/Android%20CI/badge.svg)

![GitHub stars](https://img.shields.io/github/stars/Nutrilog/NutriLog-App?style=social)
![GitHub forks](https://img.shields.io/github/forks/Nutrilog/NutriLog-App?style=social)
![GitHub watchers](https://img.shields.io/github/watchers/Nutrilog/NutriLog-App?style=social)

# NutriLog

![NutriLog Logo](https://github.com/Nutrilog/NutriLog-App/blob/main/art/logo.png?raw=true)

A Nutrition Tracking App That Empowers Users to Effortlessly Monitor their Daily Food and Drink Intake, Providing Insights for Healthier Dietary Choices.

## Package Structure

 ```
com.nutrilog.app
├── app                   # Application class
├── data                  	# For data handling
│   ├── datasource             	# Retrieves data from various sources
│   ├── local               	# Local Persistence Database. Room (SQLite) database
│   │   ├── dao                 # Data Access Object for Room
│   │   └── room          	# Database Instance
│   ├── pref                    # Datastore Setting Preference and Session
│   ├── remote               	# Handles remote data access API
│   └── repository		# Manages data resources
├── di                        	# Koin DI Modules
├── domain                    	# Core application models
├── presentation
│   ├── common                	# Contains common UI components
│   ├── ui
│   │   ├── about               # About us screen
│   │   ├── analysis            # Analysis screen
│   │   ├── auth               	# Auth screen
│   │   ├── base               	# Base classes for UI components.
│   │   ├── camera              # CameraX screen
│   │   ├── main               	# Main sreen Home, Profile, History nutrition
│   │   └── welcome          	# Welcome screen
│   └── widget                	# Widget on homescreen (Progress)
└── utils                     	# Extension functions

```

## 🤗 Credits

- 🤓 Icons are from [tablericons.com](https://tablericons.com)   
