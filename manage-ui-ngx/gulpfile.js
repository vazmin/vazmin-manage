const gulp = require('gulp');
const merge = require('gulp-merge-json');
gulp.task('merge:zh:json', function(){
    return gulp.src('src/main/webapp/assets/i18n/zh-cn/**/*.json')
        .pipe(merge({
            fileName: 'zh-cn.json'
        }))
        .pipe(gulp.dest('src/main/webapp/assets/i18n'));
});
gulp.task('merge:en:json', function(){
    return gulp.src('src/main/webapp/assets/i18n/en/**/*.json')
        .pipe(merge({
            fileName: 'en.json'
        }))
        .pipe(gulp.dest('src/main/webapp/assets/i18n'));
});
gulp.task('build', gulp.parallel('merge:en:json', 'merge:zh:json'));
gulp.task('default', gulp.parallel('merge:en:json', 'merge:zh:json'));