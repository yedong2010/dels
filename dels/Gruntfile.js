module.exports = function (grunt) {
    grunt.file.defaultEncoding = 'UTF-8';
    grunt.initConfig({
        pkg: grunt.file.readJSON('package.json'),
        dirs: {
            src: 'src/main/webapp/scripts',
            dest: 'src/main/webapp/dist'
        },
        vendor: {
            src: 'src/main/webapp/assets/js',
            dest: 'src/main/webapp/dist'
        },
        concat: {
            options: {
                separator: ';',
                stripBanners: true
            },
            one: {
                options: {sourceMap: true},
                src: ['<%= dirs.src %>/**.js', '<%= dirs.src %>/*/**.js', '<%= dirs.src %>/ctrl/indicators/*/**.js', '<%= dirs.src %>/ctrl/indicators/**.js', '<%= dirs.src %>/ctrl/quality/*/**.js', '<%= dirs.src %>/ctrl/sysMng/**.js','<%= dirs.src %>/ctrl/sysMng/excelTmp/**.js', '<%= dirs.src %>/ctrl/dataIndex/**.js', '<%= dirs.src %>/ctrl/chat/**.js', '<%= dirs.src %>/ctrl/analysis/*/**.js', '<%= dirs.src %>/ctrl/analysis/**.js'],
                dest: '<%= dirs.dest %>/<%= pkg.name %>.js'
            },
            two: {
                src: ['<%= vendor.src %>/html5shiv.min.js', '<%= vendor.src %>/respond.min.js', '<%= vendor.src %>/jquery/jquery-1.11.1.min.js', '<%= vendor.src %>/ztree/js/jquery.ztree.core.js', '<%= vendor.src %>/ztree/js/jquery.ztree.excheck.js', '<%= vendor.src %>/adminLTEApp.js', '<%= vendor.src %>/bootstrap/js/bootstrap.js', '<%= vendor.src %>/angular/angular.min.js', '<%= vendor.src %>/bootstrap/js/bootstrap-datetimepicker.min.js', '<%= vendor.src %>/bootstrap/js/bootstrap-datetimepicker.zh-CN.js', '<%= vendor.src %>/angular/ui-bootstrap-tpls.min.js', '<%= vendor.src %>/angular/angular-ui-router.min.js', '<%= vendor.src %>/highcharts.js', '<%= vendor.src %>/highcharts-3d.js', '<%= vendor.src %>/highcharts-more.js', '<%= vendor.src %>/angular/highcharts-ng.min.js', '<%= vendor.src %>/underscore-min.js', '<%= vendor.src %>/angular/angular-toastr.tpls.min.js', '<%= vendor.src %>/angular/angular-animate.min.js','<%= vendor.src %>/textAngular/textAngular-rangy.min.js','<%= vendor.src %>/textAngular/textAngular-sanitize.min.js','<%= vendor.src %>/textAngular/textAngular.min.js','<%= vendor.src %>/jquery-form.js'],
                dest: '<%= vendor.dest %>/vendor.js'
            }
        },
        uglify: {
            options: {
                banner: '/*! <%= pkg.name %> <%= grunt.template.today("yyyy-mm-dd") %> */\n'
            },
            builda: {
                options: {
                    report: "min",//输出压缩率，可选的值有 false(不输出信息)，gzip
                    sourceMap: true
                },
                files: {
                    '<%= dirs.dest %>/<%= pkg.name %>.min.js': ['<%= dirs.dest %>/<%= pkg.name %>.js']
                }
            },
            buildb: {
                options: {
                    report: "min",//输出压缩率，可选的值有 false(不输出信息)，gzip
                    preserveComments: false,
                    sourceMap: false
                },
                files: {
                    '<%= dirs.dest %>/vendor.min.js': ['<%= dirs.dest %>/vendor.js']
                }
            }
        },
        cssmin: {
            options: {
                banner: '/!*! <%= pkg.name %> <%= grunt.template.today("yyyy-mm-dd") %> *!/\n',
                compatibility: 'ie8',
                keepSpecialComments: 0,
                noAdvanced: true
            },
            target: {
                files: [{
                    expand: true,
                    cwd: 'src/main/webapp/assets/css',
                    src: ['style.css'],
                    dest: 'src/main/webapp/assets/css/',
                    ext: '.min.css'
                }]
            }
        },
        watch: {
            configFiles: {
                files: ['Gruntfile.js'],
                options: {
                    reload: true
                }
            },
            css: {
                files: 'src/main/webapp/assets/css/style.css',
                tasks: ['cssmin'],
                options: {
                    livereload: true
                }
            },
            scripts: {
                files: ['src/main/webapp/scripts/**/*.js'],
                tasks: ['merge', 'mina'],
                options: {
                    spawn: false
                }
            }
        },
        ngdocs: {
            options: {
                dest: 'docs',
                scripts: ['<%= vendor.src %>/angular/angular.min.js', '<%= vendor.src %>/angular/angular-ui-router.min.js', '<%= vendor.src %>/angular/highcharts-ng.min.js', '<%= vendor.src %>/underscore-min.js'],
                html5Mode: true,
                startPage: '/api',
                title: "API Docs",
                titleLink: "/api",
                inlinePartials: true,
                bestMatch: true,
                analytics: {
                    account: 'UA-08150815-0'
                },
                discussions: {
                    shortName: 'my',
                    url: 'http://my-domain.com',
                    dev: false
                }
            },
            tutorial: {
                src: ['content/tutorial/*.ngdoc'],
                title: 'Tutorial'
            },
            api: {
                src: ['<%= dirs.src %>/app.js', '<%= dirs.src %>/service/common.js', '<%= dirs.src %>/common/util.js', '<%= dirs.src %>/ctrl/indicators/totality/mainCtrl.js', '<%= dirs.src %>/ctrl/indexCtrl.js', '<%= dirs.src %>/ctrl/indicators/rate/rateCtrl.js', '<%= dirs.src %>/ctrl/indicators/rate/rateDetailCtrl.js', '<%= dirs.src %>/ctrl/indicators/business/busProcessCtrl.js', '<%= dirs.src %>/ctrl/indicators/business/noBusMattersCtrl.js', '<%= dirs.src %>/ctrl/indicators/business/noBusMattersDetailCtrl.js', '<%= dirs.src %>/ctrl/quality/report/qualityCtrl.js', '<%= dirs.src %>/ctrl/indicators/business/abnormalCtrl.js', '<%= dirs.src %>/ctrl/quality/totality/zlCtrl.js', '<%= dirs.src %>/ctrl/quality/report/cwlbxqCtrl.js', '<%= dirs.src %>/ctrl/indicators/rate/enterBusDetailCtrl.js', '<%= dirs.src %>/ctrl/indicators/rate/enterDepartmentDetailCtrl.js', '<%= dirs.src %>/ctrl/indicators/rate/enterSituationCtrl.js'],
                title: 'API Documentation'
            }
        }
    });

    // 加载包含 "uglify" 任务的插件
    grunt.loadNpmTasks('grunt-contrib-uglify');

    // 加载包含 "concat" 任务的插件
    grunt.loadNpmTasks('grunt-contrib-concat');

    // 加载包含 "cssmin" 任务的插件
    grunt.loadNpmTasks('grunt-contrib-cssmin');

    // 加载包含 "watch" 任务的插件
    grunt.loadNpmTasks('grunt-contrib-watch');

    // 加载包含 "watch" 任务的插件
    grunt.loadNpmTasks('grunt-ngdocs');

    // 默认被执行的任务列表。
    grunt.registerTask('default', ['watch', 'concat', 'uglify', 'cssmin']);
    grunt.registerTask('mina', ['uglify:builda']);
    grunt.registerTask('merge', ['concat:one']);
    grunt.registerTask('minb', ['uglify:buildb']);

}