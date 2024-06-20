![GitHub Cards Preview](https://github.com/Nutrilog/NutriLog-App/blob/main/art/nutrilog-cover.png?raw=true)

![Build (Android)](https://github.com/Nutrilog/NutriLog-App/workflows/Android%20CI/badge.svg)

![GitHub stars](https://img.shields.io/github/stars/Nutrilog/NutriLog-App?style=social)
![GitHub forks](https://img.shields.io/github/forks/Nutrilog/NutriLog-App?style=social)
![GitHub watchers](https://img.shields.io/github/watchers/Nutrilog/NutriLog-App?style=social)

# NutriLog

![NutriLog Logo](https://github.com/Nutrilog/NutriLog-App/blob/main/art/logo.png?raw=true)

A Nutrition Tracking App That Empowers Users to Effortlessly Monitor their Daily Food and Drink Intake, Providing Insights for Healthier Dietary Choices.

## 🛠️ Installation

Download the APK from the [latest release](https://github.com/Nutrilog/NutriLog-App/releases/latest)
and install it on your Android device.

## 🚀 Features

| Dashboard                                                                 | Profile                                                                 | About                                                                 |
|---------------------------------------------------------------------------|-------------------------------------------------------------------------|-----------------------------------------------------------------------|
| ![](https://github.com/Nutrilog/NutriLog-App/blob/main/art/dashboard.png) | ![](https://github.com/Nutrilog/NutriLog-App/blob/main/art/profile.png) | ![](https://github.com/Nutrilog/NutriLog-App/blob/main/art/about.png) |

| History                                                                                                                                                | Analysis/Detection Image                                                                                                                                 | Register & Login                                                                                                                               |
|--------------------------------------------------------------------------------------------------------------------------------------------------------|----------------------------------------------------------------------------------------------------------------------------------------------------------|------------------------------------------------------------------------------------------------------------------------------------------------|
| ![](https://github.com/Nutrilog/NutriLog-App/blob/main/art/history.png) ![](https://github.com/Nutrilog/NutriLog-App/blob/main/art/history_detail.png) | ![](https://github.com/Nutrilog/NutriLog-App/blob/main/art/analysis.png) ![](https://github.com/Nutrilog/NutriLog-App/blob/main/art/analysis_result.png) | ![](https://github.com/Nutrilog/NutriLog-App/blob/main/art/register.png) ![](https://github.com/Nutrilog/NutriLog-App/blob/main/art/login.png) |

## 📱 Preview

| Authentication                                                                            | First Time User                                                                                     | Dashboard                                                                                           | Profile                                                                                         |
|-------------------------------------------------------------------------------------------|-----------------------------------------------------------------------------------------------------|-----------------------------------------------------------------------------------------------------|-------------------------------------------------------------------------------------------------|
| ![Auth](https://github.com/Nutrilog/NutriLog-App/blob/main/art/auth_preview.gif?raw=true) | ![FTU](https://github.com/Nutrilog/NutriLog-App/blob/main/art/first_time_user_preview.gif?raw=true) | ![Dashboard](https://github.com/Nutrilog/NutriLog-App/blob/main/art/dashboard_preview.gif?raw=true) | ![Profile](https://github.com/Nutrilog/NutriLog-App/blob/main/art/profile_preview.gif?raw=true) |

| History                                                                                         | Analysis                                                                                          | About                                                                                       |
|-------------------------------------------------------------------------------------------------|---------------------------------------------------------------------------------------------------|---------------------------------------------------------------------------------------------|
| ![History](https://github.com/Nutrilog/NutriLog-App/blob/main/art/history_preview.gif?raw=true) | ![Analysis](https://github.com/Nutrilog/NutriLog-App/blob/main/art/analysis_preview.gif?raw=true) | ![About](https://github.com/Nutrilog/NutriLog-App/blob/main/art/about_preview.gif?raw=true) |

## Package Structure

 ```
com.nutrilog.app
├── app                     # Application class
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

## 🗼 Architecture

This app uses [***MVVM (Model View
View-Model)***](https://developer.android.com/jetpack/docs/guide#recommended-app-arch) architecture.

![MVVM](https://github.com/Nutrilog/NutriLog-App/blob/main/art/MVVM.jpg?raw=true)

## 🤗 Credits

- 🤓 Icons are from [tablericons.com](https://tablericons.com)   
