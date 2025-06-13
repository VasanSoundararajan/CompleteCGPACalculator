# 🎓 Android CGPA Calculator App

This Android application allows students to calculate their **SGPA (per semester)** and **CGPA (cumulative)** based on the number of semesters, subjects, grade points, and credits entered. Built using **Java** and **Android SDK**, the app provides a dynamic and user-friendly interface for academic performance tracking.

---

## 🚀 Features

- 📅 Enter the **number of semesters** dynamically
- 🧾 Input the **number of subjects** for each semester
- 📈 Enter **grade points** and **credit values** per subject
- 📚 Automatically calculates **SGPA** for each semester
- 🧮 Computes the **CGPA** across all semesters
- ♻️ Clear/reset all data easily
- 💡 Validations included for empty or invalid inputs

---

## 📱 UI Components

- `EditText` for inputting number of semesters, subjects, grades, and credits
- `Button` to generate input fields and calculate results
- `TextView` to display SGPA and CGPA
- `LinearLayout` to dynamically add/remove semester and subject blocks

---

## 🧠 Logic Overview

### SGPA Calculation:
```java
SGPA = Σ (Grade Point × Credit) / Σ (Credits)
````

### CGPA Calculation:

```java
CGPA = Σ (All SGPA × Corresponding Semester Credits) / Σ (Total Credits)
```

* Uses `BigDecimal` for precise arithmetic and rounding
* Ensures all grade points are between **5 and 10**
* Avoids division by zero through checks

---

## 🛠️ Tech Stack

* **Language**: Java
* **Framework**: Android SDK
* **UI**: XML, Java (Dynamic Layouts)
* **Target SDK**: Android 8.0 (API 26) and above


---

## 🧪 How to Run

1. **Clone the repo** or download the project.
2. Open it in **Android Studio**.
3. Connect a device or use an emulator.
4. Run the app using `Run > Run 'app'`.

---

## ✏️ TODOs & Enhancements

* [ ] Save entered data locally (SharedPreferences or Room)
* [ ] Export result as PDF or share via intent
* [ ] Add GPA scale selection (e.g., 4.0 / 10.0)
* [ ] Support for subject name inputs
* [ ] Theming and accessibility improvements

---

## 📄 License

This project is open-source and free to use for personal or educational purposes.

---

## 👨‍💻 Author

**Vasan S**
📧 [vasansoundararajan.21@gmail.com](mailto:vasansoundararajan.21@gmail.com)
🌐 [vasanportfolio.com](https://vasansoundararajan.github.io/Portfolio/)
📱 [LinkedIn](https://www.linkedin.com/in/vasan-s-624b34253)

---

```

Let me know if you'd like a Play Store banner mockup, feature graphic, or the app packaged into `.apk` format.
```
