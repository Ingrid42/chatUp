var path = require('path');
var gulp = require('gulp');
var plugins = require('gulp-load-plugins')();

plugins.livereload = require('gulp-livereload');
plugins.sass = require('gulp-ruby-sass');
plugins.webpack = require('webpack-stream');

var source = './src';
var destination = './dist';

var configWebpack = {
  output: {
    filename: 'bundle.js'
  },
  module: {
    loaders: [
      {
        test : /\.jsx?$/,
        include : path.resolve(__dirname, 'src/js'),
        loader : 'babel'
      }
    ]
  }
};

gulp.task('webpack', function() {
  return gulp.src(source + '/js/index.js')
  .pipe(plugins.webpack(configWebpack))
  .pipe(gulp.dest(destination + '/js'))
  .pipe(plugins.livereload());
});

gulp.task('compileCSS', () =>
    plugins.sass(source + '/scss/index.scss', {
      sourcemap: false,
    })
    .on('error', plugins.sass.logError)
    .pipe(plugins.csscomb())
    .pipe(plugins.cssbeautify({indent: '  '}))
    .pipe(plugins.autoprefixer())
    .pipe(gulp.dest(destination + '/css/'))
    .pipe(plugins.livereload())
);

gulp.task('minifyCSS', function () {
  return gulp.src(destination + '/ressources/styles/index.css')
    .pipe(plugins.csso())
    .pipe(plugins.rename({
      suffix: '.min'
    }))
    .pipe(gulp.dest(destination + '/ressources/styles/'));
});


gulp.task('copyHTML', function() {
  return gulp.src(source + '/index.html')
  .pipe(gulp.dest(destination))
  .pipe(plugins.livereload());
});


gulp.task('copyIMG', function() {
  return gulp.src(source + '/img/*')
  .pipe(gulp.dest(destination + '/img/'))
  .pipe(plugins.livereload());
});


gulp.task('copyHTMLDependancies', function() {
  gulp.src(source + '/html/*.html')
  .pipe(gulp.dest(destination + '/html'))
  .pipe(plugins.livereload());

  gulp.src(source + '/html/*/*.html')
  .pipe(gulp.dest(destination + '/html'))
  .pipe(plugins.livereload());

  gulp.src(source + '/html/*/*/*.html')
  .pipe(gulp.dest(destination + '/html'))
  .pipe(plugins.livereload());
});


gulp.task('minifyJS', function() {
  return gulp.src(source + '/js/*.js')
    .pipe(plugins.uglify())
    .pipe(plugins.rename({
      suffix: '.min'
    }))
    .pipe(gulp.dest(destination + '/js/'));
});


gulp.task('watch', function () {
  plugins.livereload.listen();
  gulp.watch(source + '/scss/*.scss', ['compileCSS', 'minifyCSS']);
  gulp.watch(source + '/index.html', ['copyHTML']);
  gulp.watch(source + '/html/*.html', ['copyHTMLDependancies']);
  gulp.watch(source + '/html/*/*.html', ['copyHTMLDependancies']);
  gulp.watch(source + '/html/*/*/*.html', ['copyHTMLDependancies']);
  gulp.watch(source + '/js/*.js', ['webpack']);
  gulp.watch(source + '/js/classes/*.js', ['webpack']);
});


gulp.task('build', ['copyHTML', 'copyHTMLDependancies', 'copyIMG', 'compileCSS', 'webpack']);
gulp.task('minify', ['minifyCSS']);
gulp.task('all', ['build',  'minify']);
gulp.task('default', ['all']);
