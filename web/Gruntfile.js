// Generated on 2015-06-10 using generator-angular-fullstack 2.0.13
'use strict';

module.exports = function(grunt) {

  var sass = require('node-sass');

  // Load grunt tasks automatically, when needed
  require('jit-grunt')(grunt, {
    connect: 'grunt-contrib-connect',
    useminPrepare: 'grunt-usemin',
    ngtemplates: 'grunt-angular-templates',
    injector: 'grunt-injector'
  });

  // Time how long tasks take. Can help when optimizing build times
  require('time-grunt')(grunt);

  var serveStatic = require('serve-static');

  // Define the configuration for all the tasks
  grunt.initConfig({

    // Project settings
    pkg: grunt.file.readJSON('package.json'),
    config: {
      // configurable paths
      client: 'src/main/webapp',
      node_modules: 'node_modules',
      dist: 'build/webapp',
      tmp: 'build/.tmp'
    },
    connect: {
      dev: {
        options: {
          port: 8960,
          hostname: '*',
          middleware: function(connect, options, middlewares) {
            return [
              require('connect-livereload')(),
              connect().use(
                  '/index.html',
                  serveStatic(__dirname + '/src/main/webapp/index.html') // jshint ignore:line
              ),
              connect().use(
                  '/welcome.html',
                  serveStatic(__dirname + '/src/main/webapp/welcome.html') // jshint ignore:line
              ),
              connect().use(
                  '/app',
                  serveStatic(__dirname + '/src/main/webapp/app') // jshint ignore:line
              ),
              connect().use(
                  '/components',
                  serveStatic(__dirname + '/src/main/webapp/components') // jshint ignore:line
              ),
              require('http-proxy-middleware')({
                target: 'http://localhost:8060',
                logLevel: 'info'
              })
            ];
          }
        }
      }
    },
    open: {
      dev: {
        url: 'http://localhost:8960/welcome.html'
      }
    },
    watch: {
      injectJS: {
        files: [
          '<%= config.client %>/{app,components}/**/*.js',
          '!<%= config.client %>/{app,components}/**/*.spec.js',
          '!<%= config.client %>/{app,components}/**/*.mock.js',
          '!<%= config.client %>/app/app.main.js',
          '!<%= config.client %>/app/app.main.test.js'],
        tasks: ['injector:scripts']
      },
      injectCss: {
        files: [
          '<%= config.client %>/{app,components}/**/*.css'
        ],
        tasks: ['injector:css']
      },
      jsTest: {
        files: [
          '<%= config.client %>/{app,components}/**/*.spec.js',
          '<%= config.client %>/{app,components}/**/*.mock.js'
        ],
        tasks: ['newer:jshint:all', 'karma']
      },
      injectSass: {
        files: [
          '<%= config.client %>/{app,components}/**/*.{scss,sass}'],
        tasks: ['injector:sass']
      },
      sass: {
        files: [
          '<%= config.client %>/{app,components}/**/*.{scss,sass}'],
        tasks: ['sass', 'postcss']
      },
      gruntfile: {
        files: ['Gruntfile.js']
      },
      livereload: {
        files: [
          '{<%= config.tmp %>,<%= config.client %>}/{app,components}/**/*.css',
          '{<%= config.tmp %>,<%= config.client %>}/{app,components}/**/*.scss',
          '{<%= config.tmp %>,<%= config.client %>}/{app,components}/**/*.html',
          '{<%= config.tmp %>,<%= config.client %>}/{app,components}/**/*.js',
          '!{<%= config.tmp %>,<%= config.client %>}{app,components}/**/*.spec.js',
          '!{<%= config.tmp %>,<%= config.client %>}/{app,components}/**/*.mock.js',
          '<%= config.client %>/assets/images/{,*//*}*.{png,jpg,jpeg,gif,webp,svg}'
        ],
        options: {
          livereload: true
        }
      }
    },

    // Make sure code styles are up to par and there are no obvious mistakes
    jshint: {
      options: {
        jshintrc: '<%= config.client %>/.jshintrc',
        reporter: require('jshint-stylish'),
        force: false
      },
      all: [
        '<%= config.client %>/{app,components}/**/*.js',
        '!<%= config.client %>/{app,components}/**/*.spec.js',
        '!<%= config.client %>/{app,components}/**/*.mock.js'
      ],
      test: {
        src: [
          '<%= config.client %>/{app,components}/**/*.spec.js',
          '<%= config.client %>/{app,components}/**/*.mock.js'
        ]
      }
    },

    // Empties folders to start fresh
    clean: {
      dist: {
        files: [
          {
            dot: true,
            src: [
              '<%= config.dist %>/*',
              '<%= config.tmp %>/*'
            ]
          }
        ]
      }
    },

    // Add vendor prefixed styles
    postcss: {
      options: {
        map: false,
        processors: [
          require('autoprefixer')({browsers: ['last 2 versions', 'ie 11']}) // add vendor prefixes
        ]
      },
      dist: {
        src: '<%= config.client %>/app/*.css'
      }
    },

    // Automatically inject Bower components into the app
    wiredep: {
      target: {
        src: [
          '<%= config.client %>/index.html',
          'karma.conf.ci.js'
        ],
        ignorePath: '<%= config.client %>/',
        exclude: [/bootstrap-sass-official/, '/bootstrap.js', '/json3/', '/es5-shim/'],
        fileTypes: {
          js: {
            block: /(([\s\t]*)\/\/\s*bower:*(\S*))(\n|\r|.)*?(\/\/\s*endbower)/gi,
            detect: {
              js: /".*\.js"/gi
            },
            replace: {
              js: '\'<%= config.client %>/{{filePath}}\','
            }
          }
        }
      }
    },

    // Renames files for browser caching purposes
    filerev: {
      dist: {
        files: [
          {
            src: [
              '<%= config.dist %>/{,*/}*.js',
              '<%= config.dist %>/{,*/}*.css',
              '<%= config.dist %>/assets/images/{,*/}*.{png,jpg,jpeg,gif,webp,svg}',
              '<%= config.dist %>/assets/fonts/*'
            ]
          }
        ]
      }
    },

    concat: {
      options: {
        sourceMap: true
      }
    },

    uglify: {
      options: {
        sourceMap: true,
        sourceMapIn: function(uglifySource) {
          return uglifySource + '.map';
        },
        sourceMapIncludeSources: true
      }
    },

    // Reads HTML for usemin blocks to enable smart builds that automatically
    // concat, minify and revision files. Creates configurations in memory so
    // additional tasks can operate on them
    useminPrepare: {
      html: ['<%= config.client %>/index.html'],
      options: {
        dest: '<%= config.dist %>',
        staging: '<%= config.tmp %>'
      }
    },

    // Performs rewrites based on rev and the useminPrepare configuration
    usemin: {
      html: ['<%= config.dist %>/{,*/}*.html'],
      css: ['<%= config.dist %>/{,*/}*.css'],
      js: ['<%= config.dist %>/{,*/}*.js'],
      options: {
        assetsDirs: [
          '<%= config.dist %>',
          '<%= config.dist %>/assets/images'
        ],
        // This is so we update image references in our ng-templates
        patterns: {
          js: [
            [/(assets\/images\/.*?\.(?:gif|jpeg|jpg|png|webp|svg))/gm,
              'Update the JS to reference our revved images']
          ]
        }
      }
    },

    // Allow the use of non-minsafe AngularJS files. Automatically makes it
    // minsafe compatible so Uglify does not destroy the ng references
    ngAnnotate: {
      dist: {
        files: [
          {
            expand: true,
            cwd: '<%= config.tmp %>/concat',
            src: '*/**.js',
            dest: '<%= config.tmp %>/concat'
          }
        ]
      }
    },

    // Package all the html partials into a single javascript payload
    ngtemplates: {
      options: {
        // This should be the name of your apps angular module
        module: 'privatlakareApp',
        htmlmin: {
          collapseBooleanAttributes: true,
          collapseWhitespace: true,
          removeAttributeQuotes: true,
          removeEmptyAttributes: true,
          removeRedundantAttributes: true,
          removeScriptTypeAttributes: true,
          removeStyleLinkTypeAttributes: true
        },
        usemin: 'app/app.main.js'
      },
      main: {
        cwd: '<%= config.client %>',
        src: ['{app,components}/**/*.html'],
        dest: '<%= config.tmp %>/templates.js',
        options: {
          prefix: '/'
        }
      }
    },

    // Copies remaining files to places other tasks can use
    copy: {
      dist: {
        files: [
          {
            expand: true,
            dot: true,
            cwd: '<%= config.client %>',
            dest: '<%= config.dist %>',
            src: [
              'assets/**/*',
              'bower_components/**/*',
              'WEB-INF/**/*',
              '*.*'
            ]
          },
          {
            expand: true,
            cwd: '<%= config.tmp %>/images',
            dest: '<%= config.dist %>/assets/images',
            src: ['generated/*']
          }
        ]
      },
      styles: {
        expand: true,
        cwd: '<%= config.client %>',
        dest: '<%= config.tmp %>/',
        src: ['{app,components}/**/*.css']
      }
    },

    // Test settings
    karma: {
      unit: {
        configFile: 'karma.conf.ci.js',
        singleRun: true
      }
    },

    // Compiles Sass to CSS
    sass: {
      options: {
        implementation: sass,
        includePaths: [
          '<%= config.client %>/bower_components',
          '<%= config.client %>/app',
          '<%= config.client %>/components',
          '<%= config.node_modules %>/compass-mixins/lib'
        ]
      },
      client: {
        files: {
          '<%= config.client %>/app/app.css': '<%= config.client %>/app/app.scss'
        }
      }
    },

    injector: {
      options: {
        lineEnding: grunt.util.linefeed
      },
      // Inject application script files into index.html (doesn't include bower)
      scripts: {
        options: {
          transform: function(filePath) {
            filePath = filePath.replace('/src/main/webapp/', '');
            filePath = filePath.replace('/<%= config.tmp %>/', '');
            return '<script src="' + filePath + '"></script>';
          },
          starttag: '<!-- injector:js -->',
          endtag: '<!-- endinjector -->'
        },
        files: {
          '<%= config.client %>/index.html': [
            ['{<%= config.tmp %>,<%= config.client %>}/{app,components}/**/*.js',
              '!{<%= config.tmp %>,<%= config.client %>}/app/app.main.js',
              '!{<%= config.tmp %>,<%= config.client %>}/app/app.main.test.js',
              '!{<%= config.tmp %>,<%= config.client %>}/{app,components}/**/*.spec.js',
              '!{<%= config.tmp %>,<%= config.client %>}/{app,components}/**/*.mock.js']
          ]
        }
      },

      // Inject component scss into app.scss
      sass: {
        options: {
          transform: function(filePath) {
            filePath = filePath.replace('/src/main/webapp/app/', '');
            filePath = filePath.replace('/src/main/webapp/components/', '');
            return '@import \'' + filePath + '\';';
          },
          starttag: '// injector',
          endtag: '// endinjector'
        },
        files: {
          '<%= config.client %>/app/app.scss': [
            '<%= config.client %>/{app,components}/**/*.{scss,sass}',
            '!<%= config.client %>/app/app.{scss,sass}'
          ]
        }
      },

      // Inject component css into index.html
      css: {
        options: {
          transform: function(filePath) {
            filePath = filePath.replace('/src/main/webapp/', '');
            filePath = filePath.replace('/<%= config.tmp %>/', '');
            return '<link rel="stylesheet" href="/' + filePath + '">';
          },
          starttag: '<!-- injector:css -->',
          endtag: '<!-- endinjector -->'
        },
        files: {
          '<%= config.client %>/index.html': [
            '<%= config.client %>/{app,components}/**/*.css'
          ]
        }
      }
    }
  });

  // Used for delaying livereload until after server has restarted
  grunt.registerTask('wait', function() {
    grunt.log.ok('Waiting for server reload...');

    var done = this.async();

    setTimeout(function() {
      grunt.log.writeln('Done waiting!');
      done();
    }, 1500);
  });

  grunt.registerTask('connect-keepalive', 'Keep grunt running', function() {
    this.async();
  });

  grunt.registerTask('serve', [
    'injector:sass',
    'sass',
    'postcss',
    'injector',
    'wiredep',
    'connect:dev',
    'wait',
    'open',
    'watch'
  ]);

  grunt.registerTask('test', [
    'injector',
    'karma'
  ]);

  grunt.registerTask('build_no_minify', [
    'clean:dist',
    'jshint',
    'injector:sass',
    'sass',
    'postcss',
    'injector:scripts',
    'injector:css',
    'wiredep',
    'karma'
  ]);

  grunt.registerTask('build_no_minify_no_karma', [
    'clean:dist',
    'jshint',
    'injector:sass',
    'sass',
    'postcss',
    'injector:scripts',
    'injector:css',
    'wiredep'
  ]);

  grunt.registerTask('build', [
    'clean:dist',
    'jshint',
    'copy:dist',
    'injector:sass',
    'sass',
    'postcss',
    'injector:scripts',
    'injector:css',
    'wiredep',
    'useminPrepare',
    'ngtemplates',
    'karma',
    'concat',
    'ngAnnotate',
    'cssmin',
    'uglify',
    'filerev',
    'usemin'
  ]);

  grunt.registerTask('jshintcheck', [
    'jshint'
  ]);
};
