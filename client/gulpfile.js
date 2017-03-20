var path = require('path');
var gulp = require('gulp');
var plugins = require('gulp-load-plugins')();
var livereload = require('gulp-livereload');
var sass = require('gulp-ruby-sass');

var source = './src';
var destination = './src';



gulp.task('compileCSS', () =>
    sass(source + '/scss/index.scss', {
      sourcemap: false,
    })
    .on('error', sass.logError)
    .pipe(plugins.csscomb())
    .pipe(plugins.cssbeautify({indent: '  '}))
    .pipe(plugins.autoprefixer())
    .pipe(gulp.dest(destination + '/css/'))
    .pipe(livereload())
);

gulp.task('minifyCSS', function () {
  return gulp.src(destination + '/ressources/styles/index.css')
    .pipe(plugins.csso())
    .pipe(plugins.rename({
      suffix: '.min'
    }))
    .pipe(gulp.dest(destination + '/ressources/styles/'));
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
  livereload.listen();
  gulp.watch(source + '/scss/*.scss', ['compileCSS', 'minifyCSS']);
  // gulp.watch(source + '/js/*.js', ['minifyJS']);
});


gulp.task('build', ['compileCSS']);
gulp.task('minify', ['minifyCSS']);
gulp.task('all', ['build',  'minify']);
gulp.task('default', ['all']);
