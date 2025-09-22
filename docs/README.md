# Hotel Booking Service - Training Documentation

## 📋 Overview

Dokumentasi training komprehensif untuk aplikasi Hotel Booking Service yang dibangun menggunakan Spring Boot. Dokumentasi ini dirancang khusus untuk pelatihan tim development dengan focus pada best practices dan implementasi real-world.

## 🎯 Target Audience

- **Developer Java/Spring Boot** - pemula sampai menengah
- **Team Lead** - yang ingin memahami arsitektur dan design patterns
- **Trainer/Instructor** - yang akan menggunakan materi ini untuk pelatihan
- **QA Engineer** - yang perlu memahami API testing dan troubleshooting

## 📁 Structure Documentation

```
docs/
├── index.html              # Main documentation page
├── assets/
│   ├── css/
│   │   └── style.css      # Styling untuk dokumentasi
│   ├── js/
│   │   └── script.js      # Interactive features
│   └── images/            # Screenshots dan diagrams
├── README.md              # This file
└── postman/               # Postman collection (future)
    └── hotel-booking-api.json
```

## 🚀 How to Use

### 1. View Documentation Locally

```bash
# Navigate to docs directory
cd docs/

# Open with simple HTTP server (Python)
python -m http.server 8000

# Or use Node.js http-server
npx http-server

# Then open browser at http://localhost:8000
```

### 2. Print Documentation

- Open `index.html` in browser
- Click "Print Documentation" button
- Select printer or "Save as PDF"

### 3. Share with Team

- Upload docs folder ke internal web server
- Share via git repository
- Create internal documentation site

## 📚 Content Sections

1. **📋 Overview** - Introduction dan business domain
2. **🛠️ Tools Required** - Software dependencies dan setup
3. **⚙️ Setup & Installation** - Step-by-step setup guide
4. **🏗️ Architecture** - Application architecture dan design patterns
5. **🗄️ Database** - Database schema dan relationships
6. **🚀 API Endpoints** - REST API documentation dengan examples
7. **🧪 Testing** - Testing strategies dan implementation
8. **🚢 Deployment** - Deployment options dan configurations
9. **🔧 Troubleshooting** - Common issues dan solutions
10. **📚 Best Practices** - Coding guidelines dan development workflow

## ✨ Features

- **📱 Responsive Design** - Works on desktop, tablet, dan mobile
- **🔍 Search Functionality** - Search across all documentation sections
- **📋 Copy Code Blocks** - One-click copy untuk code examples
- **🔗 Smooth Navigation** - Internal linking dan scroll behavior
- **🖨️ Print Friendly** - Optimized untuk print/PDF export
- **🌗 Interactive Elements** - Collapsible sections dan tooltips

## 🛠️ Customization

### Modify Styles
Edit `assets/css/style.css` untuk custom styling:

```css
/* Custom company colors */
:root {
    --primary-color: #your-company-color;
    --secondary-color: #your-accent-color;
}
```

### Add New Sections
1. Add navigation link di `index.html`
2. Create new section dengan proper ID
3. Update JavaScript untuk smooth scrolling

### Update Content
- Code examples di `<div class="code-block">`
- API endpoints di table sections  
- Screenshots di `assets/images/`

## 📋 Training Checklist

### Pre-Training Setup
- [ ] Verify all tools installed (Java, MySQL, IntelliJ, dll)
- [ ] Clone repository and run application
- [ ] Test API endpoints dengan Postman
- [ ] Prepare sample data untuk demo

### Training Session Flow
- [ ] **Session 1**: Overview & Setup (1-2 hours)
- [ ] **Session 2**: Architecture & Database (1-2 hours)  
- [ ] **Session 3**: API Development & Testing (2-3 hours)
- [ ] **Session 4**: Deployment & Best Practices (1-2 hours)

### Post-Training
- [ ] Code review session
- [ ] Q&A dan troubleshooting
- [ ] Additional exercises/assignments
- [ ] Documentation feedback dan improvement

## 🤝 Contributing

Untuk update dokumentasi:

1. **Fork repository**
2. **Update content** di HTML/CSS files
3. **Test locally** untuk memastikan tampilan OK
4. **Submit pull request** dengan deskripsi perubahan
5. **Review proses** oleh training team

## 📞 Support & Contact

- **📧 Email**: training-support@company.com
- **💬 Slack**: #spring-boot-training channel
- **📞 Phone**: +62-800-123-4567
- **🐛 Issues**: Create GitHub issue untuk bug reports

## 📝 Version History

- **v1.0** (2024-01-15) - Initial documentation release
- **v1.1** (TBD) - Added Postman collection
- **v1.2** (TBD) - Enhanced troubleshooting section

## 📖 References

- [Spring Boot Official Documentation](https://spring.io/projects/spring-boot)
- [Spring Data JPA Reference](https://spring.io/projects/spring-data-jpa)
- [MySQL Documentation](https://dev.mysql.com/doc/)
- [Maven Getting Started Guide](https://maven.apache.org/guides/getting-started/)

---

📌 **Note**: Dokumentasi ini adalah living document. Update berkala sesuai dengan perkembangan aplikasi dan feedback dari training sessions.

🎯 **Goal**: Menciptakan developer Spring Boot yang kompeten dan mampu mengimplementasikan best practices dalam real-world projects.