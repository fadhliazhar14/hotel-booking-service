// Hotel Booking Service Documentation JavaScript

document.addEventListener('DOMContentLoaded', function() {
    // Smooth scrolling for navigation links
    const navLinks = document.querySelectorAll('.nav a[href^="#"]');
    navLinks.forEach(link => {
        link.addEventListener('click', function(e) {
            e.preventDefault();
            const targetId = this.getAttribute('href');
            const targetSection = document.querySelector(targetId);
            if (targetSection) {
                targetSection.scrollIntoView({
                    behavior: 'smooth',
                    block: 'start'
                });
            }
        });
    });

    // Copy to clipboard functionality for code blocks
    const codeBlocks = document.querySelectorAll('.code-block');
    codeBlocks.forEach(block => {
        const copyBtn = document.createElement('button');
        copyBtn.textContent = 'Copy';
        copyBtn.className = 'copy-btn';
        copyBtn.style.cssText = `
            position: absolute;
            top: 10px;
            right: 10px;
            background: var(--secondary-color);
            color: white;
            border: none;
            padding: 5px 10px;
            border-radius: 3px;
            cursor: pointer;
            font-size: 12px;
            opacity: 0.8;
            transition: opacity 0.3s;
        `;
        
        copyBtn.addEventListener('click', function() {
            const code = block.querySelector('pre') || block;
            const text = code.textContent;
            
            navigator.clipboard.writeText(text).then(function() {
                copyBtn.textContent = 'Copied!';
                copyBtn.style.background = '#27ae60';
                setTimeout(() => {
                    copyBtn.textContent = 'Copy';
                    copyBtn.style.background = 'var(--secondary-color)';
                }, 2000);
            }).catch(function() {
                console.error('Failed to copy code');
            });
        });
        
        block.appendChild(copyBtn);
    });

    // Toggle sections functionality
    const sectionHeaders = document.querySelectorAll('.section-header');
    sectionHeaders.forEach(header => {
        header.style.cursor = 'pointer';
        header.addEventListener('click', function() {
            const content = this.nextElementSibling;
            if (content && content.classList.contains('section-content')) {
                content.style.display = content.style.display === 'none' ? 'block' : 'none';
                
                // Add/remove arrow indicator
                let arrow = header.querySelector('.toggle-arrow');
                if (!arrow) {
                    arrow = document.createElement('span');
                    arrow.className = 'toggle-arrow';
                    arrow.textContent = ' ▼';
                    header.appendChild(arrow);
                }
                arrow.textContent = content.style.display === 'none' ? ' ▶' : ' ▼';
            }
        });
    });

    // Search functionality
    function addSearchFunctionality() {
        const searchInput = document.getElementById('search-input');
        if (searchInput) {
            searchInput.addEventListener('input', function() {
                const searchTerm = this.value.toLowerCase();
                const sections = document.querySelectorAll('.section');
                
                sections.forEach(section => {
                    const text = section.textContent.toLowerCase();
                    if (searchTerm === '' || text.includes(searchTerm)) {
                        section.style.display = 'block';
                    } else {
                        section.style.display = 'none';
                    }
                });
            });
        }
    }

    // Initialize search if search input exists
    addSearchFunctionality();

    // Highlight current section in navigation
    function highlightCurrentSection() {
        const sections = document.querySelectorAll('.section[id]');
        const navLinks = document.querySelectorAll('.nav a');
        
        window.addEventListener('scroll', function() {
            let currentSection = '';
            
            sections.forEach(section => {
                const rect = section.getBoundingClientRect();
                if (rect.top <= 100 && rect.bottom >= 100) {
                    currentSection = section.id;
                }
            });
            
            navLinks.forEach(link => {
                link.classList.remove('active');
                if (link.getAttribute('href') === '#' + currentSection) {
                    link.classList.add('active');
                }
            });
        });
    }

    highlightCurrentSection();

    // Add active class styles
    const style = document.createElement('style');
    style.textContent = `
        .nav a.active {
            background: var(--primary-color) !important;
            color: var(--white) !important;
        }
        
        .copy-btn:hover {
            opacity: 1 !important;
        }
        
        .section-header:hover {
            background: var(--dark-color) !important;
        }
    `;
    document.head.appendChild(style);

    // Back to top button
    const backToTopBtn = document.createElement('button');
    backToTopBtn.innerHTML = '↑';
    backToTopBtn.className = 'back-to-top';
    backToTopBtn.style.cssText = `
        position: fixed;
        bottom: 20px;
        right: 20px;
        background: var(--secondary-color);
        color: white;
        border: none;
        width: 50px;
        height: 50px;
        border-radius: 50%;
        cursor: pointer;
        font-size: 20px;
        opacity: 0;
        visibility: hidden;
        transition: all 0.3s ease;
        z-index: 1000;
    `;

    document.body.appendChild(backToTopBtn);

    window.addEventListener('scroll', function() {
        if (window.pageYOffset > 300) {
            backToTopBtn.style.opacity = '1';
            backToTopBtn.style.visibility = 'visible';
        } else {
            backToTopBtn.style.opacity = '0';
            backToTopBtn.style.visibility = 'hidden';
        }
    });

    backToTopBtn.addEventListener('click', function() {
        window.scrollTo({
            top: 0,
            behavior: 'smooth'
        });
    });

    // Print functionality
    window.printDoc = function() {
        window.print();
    };

    console.log('Hotel Booking Service Documentation loaded successfully!');
});