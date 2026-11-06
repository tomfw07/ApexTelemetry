
(() => {
    const $ = (selector) => document.querySelector(selector);

    const grid = $('#story-grid');
    const empty = $('#empty-state');
    const articleView = $('#article-view');
    const featured = $('#featured-story');
    const year = $('#year');

    if (year) {
        year.textContent = new Date().getFullYear();
    }

    const escapeHTML = (value = '') =>
        String(value).replace(/[&<>"']/g, (char) => ({
            '&': '&amp;',
            '<': '&lt;',
            '>': '&gt;',
            '"': '&quot;',
            "'": '&#39;'
        }[char]));

    const slugify = (value = '') =>
        String(value)
            .toLowerCase()
            .normalize('NFKD')
            .replace(/[^a-z0-9]+/g, '-')
            .replace(/^-|-$/g, '');

    const formatDate = (value) => {
        if (!value) return 'ANALYSIS';

        const date = new Date(`${value}T12:00:00`);

        return Number.isNaN(date.getTime())
            ? escapeHTML(value)
            : date.toLocaleDateString('en-GB', {
                day: 'numeric',
                month: 'short',
                year: 'numeric'
            }).toUpperCase();
    };

    const postURL = (post) =>
        `#story/${encodeURIComponent(post.id || slugify(post.title))}`;

    function card(post, index) {
        const el = document.createElement('article');

        el.className = `story-card${index === 0 ? ' featured' : ''}`;

        el.innerHTML = `
      <div class="story-card-top">
        <span class="category">${escapeHTML(post.category || 'RACE ANALYSIS')}</span>
        <span>${formatDate(post.date)}</span>
      </div>
      <div class="story-card-body">
        <h3>${escapeHTML(post.title || 'Untitled analysis')}</h3>
        <p>${escapeHTML(post.excerpt || 'Evidence-led Formula 1 analysis from available data.')}</p>
        <a class="read-link" href="${postURL(post)}">
          Read the analysis <span>↗</span>
        </a>
      </div>
    `;

        return el;
    }

    function showPost(post) {
        if (!post || !articleView) return;

        if (grid) grid.hidden = true;
        if (empty) empty.hidden = true;
        if (featured) featured.hidden = true;

        articleView.hidden = false;

        const paragraphs = Array.isArray(post.body) ? post.body : [];
        const evidence = Array.isArray(post.evidence) ? post.evidence : [];

        const bodyHTML = paragraphs.map((line) => {
            if (line.startsWith('WHAT THE DATA SHOWS')) {
                return '<h3>What the data shows</h3>';
            }

            if (
                line.startsWith('WHAT IT DOESN’T PROVE') ||
                line.startsWith("WHAT IT DOESN'T PROVE")
            ) {
                return '<h3>What it does not prove</h3>';
            }

            if (line.startsWith('• ')) {
                return `<p>• ${escapeHTML(line.slice(2))}</p>`;
            }

            return line.trim() ? `<p>${escapeHTML(line)}</p>` : '';
        }).join('');

        const evidenceHTML = evidence.length
            ? `<h3>Evidence</h3><ul>${evidence.map(
                (item) => `<li>${escapeHTML(item)}</li>`
            ).join('')}</ul>`
            : '';

        const isSuzukaArticle =
            post.id === 'suzuka-2025-verstappen-norris-qualifying';

        const galleryHTML = isSuzukaArticle
            ? `
        <section class="article-gallery">
          <h3>THE QUALIFYING STORY</h3>
          <p class="gallery-intro">
            Four slides breaking down Verstappen's 12-millisecond advantage.
          </p>

          <div class="carousel-gallery">
            <figure>
              <img
                src="assets/suzuka/slide-01.png"
                alt="Suzuka qualifying analysis headline"
                loading="lazy"
              >
              <figcaption>01 / The result</figcaption>
            </figure>

            <figure>
              <img
                src="assets/suzuka/slide-02.png"
                alt="Verstappen and Norris lap-time comparison"
                loading="lazy"
              >
              <figcaption>02 / Lap times</figcaption>
            </figure>

            <figure>
              <img
                src="assets/suzuka/slide-03.png"
                alt="Suzuka qualifying sector comparison"
                loading="lazy"
              >
              <figcaption>03 / Sector analysis</figcaption>
            </figure>

            <figure>
              <img
                src="assets/suzuka/slide-04.png"
                alt="Suzuka qualifying analysis conclusion"
                loading="lazy"
              >
              <figcaption>04 / The takeaway</figcaption>
            </figure>
          </div>
        </section>
      `
            : '';

        const caveatHTML = post.caveat
            ? `<div class="article-caveat">
          <strong>Context &amp; limitations</strong><br>
          ${escapeHTML(post.caveat)}
        </div>`
            : '';

        articleView.innerHTML = `
      <a class="article-back" href="#latest">← BACK TO ALL ANALYSIS</a>

      <div class="article-meta">
        ${escapeHTML(post.category || 'RACE ANALYSIS')}
        &nbsp; / &nbsp;
        ${formatDate(post.date)}
      </div>

      <h2>${escapeHTML(post.title || '')}</h2>

      <p class="article-lede">${escapeHTML(post.excerpt || '')}</p>

      <div class="article-content">
        ${bodyHTML}
        ${evidenceHTML}
      </div>

      ${galleryHTML}
      ${caveatHTML}

      <p class="muted" style="font-size:11px;margin-top:28px">
        Independent educational analysis using public data.
        Review source context and limitations before drawing conclusions.
      </p>
    `;

        articleView.scrollIntoView({
            behavior: 'smooth',
            block: 'start'
        });
    }

    function route(posts) {
        const match = decodeURIComponent(location.hash)
            .match(/^#story\/(.+)$/);

        if (!match) {
            if (articleView) articleView.hidden = true;
            if (grid) grid.hidden = false;
            if (empty) empty.hidden = posts.length > 0;
            if (featured) featured.hidden = true;
            return;
        }

        const post = posts.find(
            (item) => String(item.id || slugify(item.title)) === match[1]
        );

        if (post) {
            showPost(post);
        } else {
            if (articleView) articleView.hidden = true;
            if (grid) grid.hidden = false;
            if (empty) empty.hidden = posts.length > 0;
        }
    }

    function render(posts) {
        if (!Array.isArray(posts) || posts.length === 0) {
            if (empty) empty.hidden = false;
            return;
        }

        const sorted = [...posts].sort((a, b) =>
            String(b.date || '').localeCompare(String(a.date || ''))
        );

        if (grid) {
            grid.replaceChildren();

            sorted.forEach((post, index) => {
                grid.appendChild(card(post, index));
            });

            grid.hidden = false;
        }

        if (empty) empty.hidden = true;

        window.addEventListener('hashchange', () => route(sorted));

        route(sorted);
    }

    fetch('posts.json', { cache: 'no-store' })
        .then((response) => {
            if (!response.ok) {
                throw new Error('Could not load posts.json');
            }

            return response.json();
        })
        .then(render)
        .catch((error) => {
            console.error('Apex Telemetry newsroom error:', error);

            if (empty) empty.hidden = false;
        });
})();
