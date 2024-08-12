# Grade Calculator App

## Overview

The **Grade Calculator App** is an Android application designed to help students manage and calculate their grades across different courses. Users can add courses, input grades for various categories, and calculate their overall weighted grades. Additionally, the app allows users to override calculated grades, set base GPA values, and calculate their cumulative GPA.

## Features

- **Course Management**: Add, edit, and delete courses.
- **Grade Calculation**: Input grades for course categories, and the app calculates the total weighted grade and letter grade.
- **GPA Calculation**: The app calculates the cumulative GPA based on entered course grades.
- **Base GPA and Credit Hours**: Set a base GPA and credit hours to be used as a starting point for GPA calculations.

## Architecture

The app follows a **MVVM (Model-View-ViewModel)** architecture pattern, which helps in separating concerns and makes the codebase easier to manage and test.

### Components

- **Model**: The `Model` layer consists of the data-related components, including:
  - **Entities**: Classes like `Course` and `GradeDetail` that represent the data models.
  - **Database**: The Room database is used for persistent storage, with `CourseDatabase`, `CourseDAO`, and `Converters` managing data access and conversion.
  
- **View**: The `View` layer consists of the UI components:
  - **Fragments**: UI screens are primarily built using fragments, including `NavHomeFragment`, `AddGradeFragment`, and `DeleteFragment`. These fragments are responsible for displaying the data and handling user interactions.
  - **Adapters**: RecyclerView adapters, such as `CourseAdapter` and `GradesAdapter`, are used to bind data to RecyclerViews for list displays.
  
- **ViewModel**: The `ViewModel` layer provides a bridge between the View and the Model:
  - **CourseViewModel**: Manages UI-related data in a lifecycle-conscious way and allows the fragments to communicate with the data layer.

## Requirements

Before you begin, ensure you have the following prerequisites:

- **Android Studio**: Version 4.0 or higher
- **Java Development Kit (JDK)**: Version 17 or higher
- **Gradle**: Built-in with Android Studio, no separate installation required
- **Android Device or Emulator**: Running Android 5.0 (Lollipop) or higher
  
## Installation

To install and run the project locally:

1. **Clone the repository**:
   ```sh
   git clone https://github.com/your-username/grade-calculator-app.git
   cd grade-calculator-app
   ```
2. **Open the project**
    - Open the project in Android Studio
      
3. **Build the project**:
    - Let Android Studio sync and build the project. Ensure you have all necessary dependencies installed.

4. **Run the app**:
    - Connect an Android device or use an emulator.
    - Click the "Run" button in Android Studio to build and deploy the app.

## Usage

1. **Adding Courses**: On the home screen, you can add a new course by entering the course name and credit hours.
2. **Managing Grades**: Click on a course to add or edit grades for different categories (e.g., assignments, exams).
3. **Calculating GPA**: The app automatically calculates the GPA based on the grades entered and displays it on the home screen.
4. **Customizing Grades**: Use the settings to set base GPA values or override letter grades as needed.

## Contributing

If you'd like to contribute to this project:

1. Fork the repository.
2. Create a new branch (`git checkout -b feature/your-feature`).
3. Make your changes and commit them (`git commit -am 'Add new feature'`).
4. Push to the branch (`git push origin feature/your-feature`).
5. Open a Pull Request.

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.
